package com.example.swapi.data.remote.dto

import com.squareup.moshi.Json

data class SpeciesDto(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String
)
