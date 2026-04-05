package com.example.swapi.data.repository

import com.example.swapi.data.local.dao.FilmDao
import com.example.swapi.data.local.dao.PersonDao
import com.example.swapi.data.local.dao.SpeciesDao
import com.example.swapi.data.local.entity.PersonEntity
import com.example.swapi.data.mapper.csvToIds
import com.example.swapi.data.mapper.toDomain
import com.example.swapi.data.mapper.toEntity
import com.example.swapi.data.mapper.toFilm
import com.example.swapi.data.mapper.toName
import com.example.swapi.data.remote.SwapiApi
import com.example.swapi.domain.model.Person
import com.example.swapi.domain.model.PersonDetails
import com.example.swapi.domain.repository.PeopleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PeopleRepositoryImpl @Inject constructor(
    private val api: SwapiApi,
    private val dao: PersonDao,
    private val speciesDao: SpeciesDao,
    private val filmDao: FilmDao
) : PeopleRepository {

    override fun observePeople(): Flow<List<Person>> =
        dao.observePeople().map { entities -> entities.map { it.toDomain() } }

    override fun observePerson(personId: String): Flow<Person?> =
        dao.observePersonById(personId).map { it?.toDomain() }

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    override fun observePersonDetails(personId: String): Flow<PersonDetails?> {
        return dao.observePersonById(personId).flatMapLatest { personEntity ->
            if (personEntity == null) return@flatMapLatest flowOf(null)

            val speciesIds = personEntity.speciesIdsCsv.csvToIds()
            val filmIds = personEntity.filmIdsCsv.csvToIds()

            val speciesFlow = if (speciesIds.isEmpty()) flowOf(emptyList())
            else speciesDao.observeByIds(speciesIds)

            val filmsFlow = if (filmIds.isEmpty()) flowOf(emptyList())
            else filmDao.observeByIds(filmIds)

            combine(speciesFlow, filmsFlow) { speciesEntities, filmEntities ->
                val speciesById = speciesEntities.associateBy { it.id }
                val filmsById = filmEntities.associateBy { it.id }

                PersonDetails(
                    person = personEntity.toDomain(),
                    species = speciesIds.mapNotNull { speciesById[it]?.toName() },
                    films = filmIds.mapNotNull { filmsById[it]?.toDomain() }
                )
            }
        }
    }

    override suspend fun refreshPeople(): Result<Unit> = runCatching {
        val entities = mutableListOf<PersonEntity>()
        var page = 1
        while (true) {
            val response = api.getPeoplePage(page)
            entities += response.results.map { it.toEntity() }
            if (response.next.isNullOrBlank()) break
            page++
        }
        dao.clearAll()
        dao.insertAll(entities)
    }

    override suspend fun refreshPersonDetails(personId: String): Result<Unit> = runCatching {
        val person = api.getPerson(personId)
        val personEntity = person.toEntity()
        dao.insert(personEntity)

        val speciesIds = personEntity.speciesIdsCsv.csvToIds()
        val filmIds = personEntity.filmIdsCsv.csvToIds()

        val speciesEntities = speciesIds.mapNotNull { id ->
            runCatching { api.getSpecies(id).toEntity() }.getOrNull()
        }
        if (speciesEntities.isNotEmpty()) speciesDao.insertAll(speciesEntities)

        val filmEntities = filmIds.mapNotNull { id ->
            runCatching { api.getFilm(id).toEntity() }.getOrNull()
        }
        if (filmEntities.isNotEmpty()) filmDao.insertAll(filmEntities)
    }
}
