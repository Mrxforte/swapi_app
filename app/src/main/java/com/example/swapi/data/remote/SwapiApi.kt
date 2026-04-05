package com.example.swapi.data.remote

import com.example.swapi.data.remote.dto.FilmDto
import com.example.swapi.data.remote.dto.FilmsResponseDto
import com.example.swapi.data.remote.dto.PeopleResponseDto
import com.example.swapi.data.remote.dto.PersonDto
import com.example.swapi.data.remote.dto.PlanetDto
import com.example.swapi.data.remote.dto.PlanetsResponseDto
import com.example.swapi.data.remote.dto.SpeciesDto
import com.example.swapi.data.remote.dto.StarshipDto
import com.example.swapi.data.remote.dto.StarshipsResponseDto
import com.example.swapi.data.remote.dto.VehicleDto
import com.example.swapi.data.remote.dto.VehiclesResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SwapiApi {

    // ── People ────────────────────────────────────────────────────────────────
    @GET("people/")
    suspend fun getPeoplePage(
        @Query("page") page: Int,
        @Query("search") search: String = ""
    ): PeopleResponseDto

    @GET("people/{personId}/")
    suspend fun getPerson(@Path("personId") personId: String): PersonDto

    // ── Films ─────────────────────────────────────────────────────────────────
    @GET("films/")
    suspend fun getFilmsPage(
        @Query("page") page: Int,
        @Query("search") search: String = ""
    ): FilmsResponseDto

    @GET("films/{filmId}/")
    suspend fun getFilm(@Path("filmId") filmId: String): FilmDto

    // ── Planets ───────────────────────────────────────────────────────────────
    @GET("planets/")
    suspend fun getPlanetsPage(
        @Query("page") page: Int,
        @Query("search") search: String = ""
    ): PlanetsResponseDto

    @GET("planets/{planetId}/")
    suspend fun getPlanet(@Path("planetId") planetId: String): PlanetDto

    // ── Species ───────────────────────────────────────────────────────────────
    @GET("species/")
    suspend fun getSpeciesPage(
        @Query("page") page: Int,
        @Query("search") search: String = ""
    ): com.example.swapi.data.remote.dto.SpeciesResponseDto

    @GET("species/{speciesId}/")
    suspend fun getSpecies(@Path("speciesId") speciesId: String): SpeciesDto

    // ── Vehicles ──────────────────────────────────────────────────────────────
    @GET("vehicles/")
    suspend fun getVehiclesPage(
        @Query("page") page: Int,
        @Query("search") search: String = ""
    ): VehiclesResponseDto

    @GET("vehicles/{vehicleId}/")
    suspend fun getVehicle(@Path("vehicleId") vehicleId: String): VehicleDto

    // ── Starships ─────────────────────────────────────────────────────────────
    @GET("starships/")
    suspend fun getStarshipsPage(
        @Query("page") page: Int,
        @Query("search") search: String = ""
    ): StarshipsResponseDto

    @GET("starships/{starshipId}/")
    suspend fun getStarship(@Path("starshipId") starshipId: String): StarshipDto
}
