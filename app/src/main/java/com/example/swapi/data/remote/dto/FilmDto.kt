package com.example.swapi.data.remote.dto

import com.squareup.moshi.Json

data class FilmDto(
    @Json(name = "title") val title: String,
    @Json(name = "opening_crawl") val openingCrawl: String,
    @Json(name = "url") val url: String
)
