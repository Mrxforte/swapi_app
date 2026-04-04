package com.example.swapi.data.repository

import com.example.swapi.data.local.dao.FilmDao
import com.example.swapi.data.local.dao.PersonDao
import com.example.swapi.data.local.dao.SpeciesDao
import com.example.swapi.data.mapper.csvToIds
import com.example.swapi.data.mapper.toDomain
import com.example.swapi.data.mapper.toEntity
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

    override fun observePeople(): Flow<List<Person>> {
        return dao.observePeople().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun observePerson(personId: String): Flow<Person?> {
        return dao.observePersonById(personId).map { entity ->
            entity?.toDomain()
        }
    }

    override fun observePersonDetails(personId: String): Flow<PersonDetails?> {
        return dao.observePersonById(personId).flatMapLatest { personEntity ->
            if (personEntity == null) return@flatMapLatest flowOf(null)

            val speciesIds = personEntity.speciesIdsCsv.csvToIds()
            val filmIds = personEntity.filmIdsCsv.csvToIds()

            val speciesFlow = if (speciesIds.isEmpty()) {
                flowOf(emptyList())
            } else {
                speciesDao.observeByIds(speciesIds)
            }

            val filmsFlow = if (filmIds.isEmpty()) {
                flowOf(emptyList())
            } else {
                filmDao.observeByIds(filmIds)
            }

            combine(speciesFlow, filmsFlow) { speciesEntities, filmEntities ->
                val speciesById = speciesEntities.associateBy { it.id }
                val filmsById = filmEntities.associateBy { it.id }

                val orderedSpecies = speciesIds.mapNotNull { speciesById[it]?.name }
                val orderedFilms = filmIds.mapNotNull { filmId ->
                    filmsById[filmId]?.toDomain()
                }

                PersonDetails(
                    person = personEntity.toDomain(),
                    species = orderedSpecies,
                    films = orderedFilms
                )
            }
        }
    }

    override suspend fun refreshPeople(): Result<Unit> {
        return runCatching {
            val response = api.getAllPeople()
            val entities = response.results.map { it.toEntity() }
            dao.clearAll()
            dao.insertAll(entities)
        }
    }

    override suspend fun refreshPersonDetails(personId: String): Result<Unit> {
        return runCatching {
            val person = api.getPerson(personId)
            val personEntity = person.toEntity()
            dao.insert(personEntity)

            val speciesIds = personEntity.speciesIdsCsv.csvToIds()
            val filmIds = personEntity.filmIdsCsv.csvToIds()

            val speciesEntities = speciesIds.mapNotNull { speciesId ->
                runCatching { api.getSpecies(speciesId).toEntity() }.getOrNull()
            }
            if (speciesEntities.isNotEmpty()) {
                speciesDao.insertAll(speciesEntities)
            }

            val filmEntities = filmIds.mapNotNull { filmId ->
                runCatching { api.getFilm(filmId).toEntity() }.getOrNull()
            }
            if (filmEntities.isNotEmpty()) {
                filmDao.insertAll(filmEntities)
            }
        }
    }
}
