package com.example.swapi.data.remote.dto

import com.squareup.moshi.Json

data class VehiclesResponseDto(
    @Json(name = "count") val count: Int,
    @Json(name = "next") val next: String?,
    @Json(name = "previous") val previous: String?,
    @Json(name = "results") val results: List<VehicleDto>
)

