package com.example.swapi.data.remote.dto

import com.squareup.moshi.Json

data class PersonDto(
    @Json(name = "name") val name: String,
    @Json(name = "gender") val gender: String,
    @Json(name = "birth_year") val birthYear: String,
    @Json(name = "height") val height: String,
    @Json(name = "mass") val mass: String,
    @Json(name = "hair_color") val hairColor: String,
    @Json(name = "skin_color") val skinColor: String,
    @Json(name = "eye_color") val eyeColor: String,
    @Json(name = "species") val species: List<String> = emptyList(),
    @Json(name = "films") val films: List<String> = emptyList(),
    @Json(name = "url") val url: String
)
