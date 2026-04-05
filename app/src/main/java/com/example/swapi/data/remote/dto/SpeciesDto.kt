package com.example.swapi.data.remote.dto

import com.squareup.moshi.Json

data class SpeciesDto(
    @Json(name = "name") val name: String,
    @Json(name = "classification") val classification: String,
    @Json(name = "designation") val designation: String,
    @Json(name = "average_height") val averageHeight: String,
    @Json(name = "skin_colors") val skinColors: String,
    @Json(name = "hair_colors") val hairColors: String,
    @Json(name = "eye_colors") val eyeColors: String,
    @Json(name = "average_lifespan") val averageLifespan: String,
    @Json(name = "homeworld") val homeworld: String?,
    @Json(name = "language") val language: String,
    @Json(name = "people") val people: List<String> = emptyList(),
    @Json(name = "films") val films: List<String> = emptyList(),
    @Json(name = "url") val url: String
)
