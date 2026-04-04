package com.example.swapi.domain.usecase

import com.example.swapi.domain.repository.PeopleRepository
import javax.inject.Inject

class RefreshPeopleUseCase @Inject constructor(
    private val repository: PeopleRepository
) {
    suspend operator fun invoke() = repository.refreshPeople()
}
