package com.example.swapi.presentation.designsystem

object AppStrings {
    const val AppName = "SWAPI Explorer"

    const val Back = "Back"

    const val SplashTitle = "SWAPI Explorer"

    const val OnboardingTitle = "Onboarding"
    const val OnboardingHeadline1 = "Welcome to SWAPI Explorer"
    const val OnboardingDescription1 = "Browse Star Wars characters with clean, card-based navigation."
    const val OnboardingHeadline2 = "Works Offline"
    const val OnboardingDescription2 = "Your latest data is cached, so details stay available without internet."
    const val OnboardingHeadline3 = "Customize Experience"
    const val OnboardingDescription3 = "Set your profile and switch theme in settings anytime."
    const val OnboardingNext = "Next"
    const val OnboardingSkip = "Skip"
    const val OnboardingAction = "Start Journey"

    const val PeopleTitle = "Characters"
    const val StarWarsCharactersTitle = "Star Wars Characters"
    const val Profile = "Profile"
    const val Settings = "Settings"
    const val SearchCharactersPlaceholder = "Search characters..."
    const val NoCharactersForQuery = "No characters match your search."
    const val NoCharactersFound = "No characters found."
    const val BirthYearPrefix = "Birth year"
    const val GenderPrefix = "Gender"
    const val HeightMassHairEyesTemplate = "Height: %s cm, Mass: %s kg, Hair: %s, Eyes: %s"

    const val DetailsTitle = "Character details"
    const val BasicInformation = "Basic Information"
    const val SpeciesTitle = "Species"
    const val FilmsTitle = "Films"
    const val NoSpeciesAvailable = "No species information available"
    const val NoFilmsAvailable = "No films information available"
    const val NamePrefix = "Name"
    const val HeightPrefix = "Height"
    const val MassPrefix = "Mass"
    const val HairColorPrefix = "Hair color"
    const val SkinColorPrefix = "Skin color"
    const val EyeColorPrefix = "Eye color"

    const val SettingsTitle = "Settings"
    const val Theme = "Theme"
    const val ThemeSystem = "System"
    const val ThemeLight = "Light"
    const val ThemeDark = "Dark"

    const val ProfileTitle = "Profile"
    const val DisplayName = "Your display name"
    const val Save = "Save"

    const val ExampleTitle = "Example screen"
    const val ExampleHeadline = "Reusable UI template"
    const val ExampleDescription = "This screen demonstrates how to compose app widgets with centralized constants."
    const val ExamplePrimaryAction = "Primary action"
    const val ExampleSecondaryAction = "Secondary action"

    const val OfflineEmptyError = "Could not load data. You might be offline and cache is empty."

    fun withValue(prefix: String, value: String): String = "$prefix: $value"
}
