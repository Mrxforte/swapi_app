package com.example.swapi.presentation.details

import com.example.swapi.domain.model.PersonDetails

data class PersonDetailsUiState(
    val isLoading: Boolean = true,
    val details: PersonDetails? = null,
    val errorMessage: String? = null
)
