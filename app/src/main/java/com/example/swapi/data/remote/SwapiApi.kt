package com.example.swapi.data.remote

import com.example.swapi.data.remote.dto.PeopleResponseDto
import com.example.swapi.data.remote.dto.PersonDto
import com.example.swapi.data.remote.dto.SpeciesDto
import com.example.swapi.data.remote.dto.FilmDto
import retrofit2.http.GET
import retrofit2.http.Path

interface SwapiApi {
    @GET("people/")
    suspend fun getAllPeople(): PeopleResponseDto

    @GET("people/{personId}/")
    suspend fun getPerson(@Path("personId") personId: String): PersonDto

    @GET("species/{speciesId}/")
    suspend fun getSpecies(@Path("speciesId") speciesId: String): SpeciesDto

    @GET("films/{filmId}/")
    suspend fun getFilm(@Path("filmId") filmId: String): FilmDto
}
