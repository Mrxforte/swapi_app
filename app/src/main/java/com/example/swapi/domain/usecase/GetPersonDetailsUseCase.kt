package com.example.swapi.domain.usecase

import com.example.swapi.domain.repository.PeopleRepository
import javax.inject.Inject

class GetPersonDetailsUseCase @Inject constructor(
    private val repository: PeopleRepository
) {
    operator fun invoke(personId: String) = repository.observePersonDetails(personId)
}
