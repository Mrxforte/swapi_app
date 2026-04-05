package com.example.swapi.presentation.navigation

object Routes {
    const val Splash = "splash"
    const val Onboarding = "onboarding"
    const val Main = "main"
    const val Settings = "settings"
    const val Profile = "profile"
    const val Example = "example"

    // Detail routes
    const val PersonDetails = "people/{${NavArgs.PersonId}}"
    const val FilmDetails = "films/{${NavArgs.FilmId}}"
    const val PlanetDetails = "planets/{${NavArgs.PlanetId}}"
    const val SpeciesDetails = "species/{${NavArgs.SpeciesId}}"
    const val VehicleDetails = "vehicles/{${NavArgs.VehicleId}}"
    const val StarshipDetails = "starships/{${NavArgs.StarshipId}}"

    // Legacy alias kept for SplashViewModel compatibility
    const val People = Main

    fun personDetails(id: String) = "people/$id"
    fun filmDetails(id: String) = "films/$id"
    fun planetDetails(id: String) = "planets/$id"
    fun speciesDetails(id: String) = "species/$id"
    fun vehicleDetails(id: String) = "vehicles/$id"
    fun starshipDetails(id: String) = "starships/$id"

    // Legacy – kept for back-compat with old usage
    const val Details = PersonDetails
    fun details(personId: String) = personDetails(personId)
}
