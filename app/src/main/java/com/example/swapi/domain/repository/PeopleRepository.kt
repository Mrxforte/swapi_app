package com.example.swapi.domain.repository

import com.example.swapi.domain.model.Person
import com.example.swapi.domain.model.PersonDetails
import kotlinx.coroutines.flow.Flow

interface PeopleRepository {
    fun observePeople(): Flow<List<Person>>
    fun observePerson(personId: String): Flow<Person?>
    fun observePersonDetails(personId: String): Flow<PersonDetails?>
    suspend fun refreshPeople(): Result<Unit>
    suspend fun refreshPersonDetails(personId: String): Result<Unit>
}
