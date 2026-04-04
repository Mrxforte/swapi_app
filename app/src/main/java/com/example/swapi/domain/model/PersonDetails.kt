package com.example.swapi.domain.model

data class PersonDetails(
    val person: Person,
    val species: List<String>,
    val films: List<FilmSummary>
)
