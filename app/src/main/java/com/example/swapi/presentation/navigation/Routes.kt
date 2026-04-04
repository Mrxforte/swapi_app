package com.example.swapi.presentation.navigation

object Routes {
    const val Splash = "splash"
    const val Onboarding = "onboarding"
    const val People = "people"
    const val Details = "details/{${NavArgs.PersonId}}"
    const val Settings = "settings"
    const val Profile = "profile"
    const val Example = "example"

    fun details(personId: String): String = "details/$personId"
}
