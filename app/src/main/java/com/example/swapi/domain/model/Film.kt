package com.example.swapi.domain.model

data class Film(
    val id: String,
    val title: String,
    val episodeId: Int,
    val openingCrawl: String,
    val director: String,
    val producer: String,
    val releaseDate: String
)

