package com.example.swapi.data.repository

import com.example.swapi.data.local.dao.FilmDao
import com.example.swapi.data.local.dao.PersonDao
import com.example.swapi.data.local.dao.SpeciesDao
import com.example.swapi.data.local.entity.FilmEntity
import com.example.swapi.data.local.entity.PersonEntity
import com.example.swapi.data.local.entity.SpeciesEntity
import com.example.swapi.data.remote.SwapiApi
import com.example.swapi.data.remote.dto.FilmDto
import com.example.swapi.data.remote.dto.PeopleResponseDto
import com.example.swapi.data.remote.dto.PersonDto
import com.example.swapi.data.remote.dto.SpeciesDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PeopleRepositoryImplTest {

    @Test
    fun refreshPeople_fetchesAllPages_andReplacesCache() = runTest {
        val personDao = FakePersonDao()
        personDao.insertAll(listOf(sampleEntity("old")))

        val api = FakeSwapiApi(
            pageResponses = mapOf(
                1 to PeopleResponseDto(
                    count = 2,
                    next = "https://swapi.dev/api/people/?page=2",
                    previous = null,
                    results = listOf(samplePersonDto("1", "Luke Skywalker"))
                ),
                2 to PeopleResponseDto(
                    count = 2,
                    next = null,
                    previous = "https://swapi.dev/api/people/?page=1",
                    results = listOf(samplePersonDto("2", "C-3PO"))
                )
            )
        )

        val repository = PeopleRepositoryImpl(
            api = api,
            dao = personDao,
            speciesDao = FakeSpeciesDao(),
            filmDao = FakeFilmDao()
        )

        val result = repository.refreshPeople()

        assertTrue(result.isSuccess)
        assertEquals(listOf(1, 2), api.requestedPages)
        assertEquals(1, personDao.clearCalls)
        assertEquals(1, personDao.insertAllCalls)
        assertEquals(listOf("1", "2"), personDao.snapshot().map { it.id })
    }

    @Test
    fun refreshPeople_returnsFailure_andKeepsCache_whenNextPageFails() = runTest {
        val personDao = FakePersonDao()
        personDao.insertAll(listOf(sampleEntity("cached")))

        val api = FakeSwapiApi(
            pageResponses = mapOf(
                1 to PeopleResponseDto(
                    count = 2,
                    next = "https://swapi.dev/api/people/?page=2",
                    previous = null,
                    results = listOf(samplePersonDto("1", "Luke Skywalker"))
                )
            ),
            failOnPage = 2
        )

        val repository = PeopleRepositoryImpl(
            api = api,
            dao = personDao,
            speciesDao = FakeSpeciesDao(),
            filmDao = FakeFilmDao()
        )

        val result = repository.refreshPeople()

        assertTrue(result.isFailure)
        assertEquals(listOf(1, 2), api.requestedPages)
        assertEquals(0, personDao.clearCalls)
        assertEquals(1, personDao.snapshot().size)
        assertEquals("cached", personDao.snapshot().first().id)
    }

    private fun samplePersonDto(id: String, name: String): PersonDto = PersonDto(
        name = name,
        gender = "male",
        birthYear = "19BBY",
        height = "172",
        mass = "77",
        hairColor = "blond",
        skinColor = "fair",
        eyeColor = "blue",
        species = emptyList(),
        films = emptyList(),
        url = "https://swapi.dev/api/people/$id/"
    )

    private fun sampleEntity(id: String): PersonEntity = PersonEntity(
        id = id,
        name = "name-$id",
        gender = "n/a",
        birthYear = "unknown",
        height = "unknown",
        mass = "unknown",
        hairColor = "unknown",
        skinColor = "unknown",
        eyeColor = "unknown",
        speciesIdsCsv = "",
        filmIdsCsv = ""
    )
}

private class FakeSwapiApi(
    private val pageResponses: Map<Int, PeopleResponseDto>,
    private val failOnPage: Int? = null
) : SwapiApi {

    val requestedPages = mutableListOf<Int>()

    override suspend fun getPeoplePage(page: Int): PeopleResponseDto {
        requestedPages += page
        if (failOnPage == page) error("Network error on page $page")
        return pageResponses[page] ?: error("Missing stub for page $page")
    }

    override suspend fun getPerson(personId: String): PersonDto {
        error("Not needed for this test")
    }

    override suspend fun getSpecies(speciesId: String): SpeciesDto {
        error("Not needed for this test")
    }

    override suspend fun getFilm(filmId: String): FilmDto {
        error("Not needed for this test")
    }
}

private class FakePersonDao : PersonDao {
    private val peopleFlow = MutableStateFlow<List<PersonEntity>>(emptyList())

    var clearCalls: Int = 0
    var insertAllCalls: Int = 0

    override fun observePeople(): Flow<List<PersonEntity>> = peopleFlow

    override fun observePersonById(personId: String): Flow<PersonEntity?> {
        return peopleFlow.map { people -> people.firstOrNull { it.id == personId } }
    }

    override suspend fun insertAll(people: List<PersonEntity>) {
        insertAllCalls++
        peopleFlow.value = people
    }

    override suspend fun insert(person: PersonEntity) {
        peopleFlow.value = peopleFlow.value.filterNot { it.id == person.id } + person
    }

    override suspend fun clearAll() {
        clearCalls++
        peopleFlow.value = emptyList()
    }

    fun snapshot(): List<PersonEntity> = peopleFlow.value
}

private class FakeSpeciesDao : SpeciesDao {
    override fun observeByIds(ids: List<String>): Flow<List<SpeciesEntity>> = flowOf(emptyList())

    override suspend fun insertAll(items: List<SpeciesEntity>) = Unit
}

private class FakeFilmDao : FilmDao {
    override fun observeByIds(ids: List<String>): Flow<List<FilmEntity>> = flowOf(emptyList())

    override suspend fun insertAll(items: List<FilmEntity>) = Unit
}

