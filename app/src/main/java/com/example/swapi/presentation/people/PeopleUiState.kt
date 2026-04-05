package com.example.swapi.presentation.people

import com.example.swapi.domain.model.Person

data class PeopleUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val people: List<Person> = emptyList(),
    val searchQuery: String = "",
    val errorMessage: String? = null
)
