package com.example.swapi.presentation.designsystem

object AppStrings {
    const val AppName = "SWAPI Explorer"
    const val Back = "Back"

    // Splash
    const val SplashTitle = "SWAPI Explorer"
    const val SplashSubtitle = "Explore the entire Star Wars galaxy"

    // Onboarding
    const val OnboardingTitle = "Onboarding"
    const val OnboardingHeadline1 = "Welcome to SWAPI Explorer"
    const val OnboardingDescription1 = "Browse all Star Wars resources: characters, films, planets, species, vehicles and starships."
    const val OnboardingHeadline2 = "Works Offline"
    const val OnboardingDescription2 = "Your latest data is cached so everything stays available without internet."
    const val OnboardingHeadline3 = "Customize Experience"
    const val OnboardingDescription3 = "Set your profile and switch theme in settings anytime."
    const val OnboardingNext = "Next"
    const val OnboardingSkip = "Skip"
    const val OnboardingAction = "Start Journey"

    // Bottom nav tab labels
    const val PeopleTab = "People"
    const val FilmsTab = "Films"
    const val PlanetsTab = "Planets"
    const val SpeciesTab = "Species"
    const val VehiclesTab = "Vehicles"
    const val StarshipsTab = "Starships"
    const val DrawerNavigation = "Navigation"
    const val DrawerMainSection = "Main"
    const val DrawerMoreSection = "More"

    // Common
    const val Profile = "Profile"
    const val Settings = "Settings"
    const val SearchPlaceholder = "Search..."
    const val NoResultsForQuery = "No results match your search."
    const val NoResultsFound = "No results found."
    const val OfflineEmptyError = "Could not load data. You might be offline and cache is empty."

    // People
    const val StarWarsCharactersTitle = "Star Wars Characters"
    const val SearchCharactersPlaceholder = "Search characters..."
    const val NoCharactersForQuery = "No characters match your search."
    const val NoCharactersFound = "No characters found."
    const val MainTab = "People"
    const val MainTabIconDescription = "People tab"

    // Person details
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
    const val BirthYearPrefix = "Birth year"
    const val GenderPrefix = "Gender"
    const val HeightMassHairEyesTemplate = "Height: %s cm  ·  Mass: %s kg  ·  Hair: %s  ·  Eyes: %s"

    // Film details
    const val EpisodePrefix = "Episode"
    const val DirectorPrefix = "Director"
    const val ProducerPrefix = "Producer"
    const val ReleaseDatePrefix = "Release date"
    const val OpeningCrawlPrefix = "Opening crawl"

    // Planet details
    const val RotationPeriodPrefix = "Rotation period"
    const val OrbitalPeriodPrefix = "Orbital period"
    const val DiameterPrefix = "Diameter"
    const val ClimatePrefix = "Climate"
    const val GravityPrefix = "Gravity"
    const val TerrainPrefix = "Terrain"
    const val SurfaceWaterPrefix = "Surface water"
    const val PopulationPrefix = "Population"

    // Species details
    const val ClassificationPrefix = "Classification"
    const val DesignationPrefix = "Designation"
    const val AverageHeightPrefix = "Average height"
    const val SkinColorsPrefix = "Skin colors"
    const val HairColorsPrefix = "Hair colors"
    const val EyeColorsPrefix = "Eye colors"
    const val AverageLifespanPrefix = "Average lifespan"
    const val LanguagePrefix = "Language"

    // Starship details
    const val ModelPrefix = "Model"
    const val ManufacturerPrefix = "Manufacturer"
    const val CostInCreditsPrefix = "Cost in credits"
    const val LengthPrefix = "Length"
    const val MaxSpeedPrefix = "Max atmosphering speed"
    const val CrewPrefix = "Crew"
    const val PassengersPrefix = "Passengers"
    const val CargoCapacityPrefix = "Cargo capacity"
    const val ConsumablesPrefix = "Consumables"
    const val HyperdriveRatingPrefix = "Hyperdrive rating"
    const val MGLTPrefix = "MGLT"
    const val StarshipClassPrefix = "Starship class"

    // Vehicle details
    const val VehicleClassPrefix = "Vehicle class"

    // Settings
    const val SettingsTitle = "Settings"
    const val Theme = "Theme"
    const val ThemeSystem = "System"
    const val ThemeLight = "Light"
    const val ThemeDark = "Dark"

    // Profile
    const val ProfileTitle = "Profile"
    const val DisplayName = "Your display name"
    const val Save = "Save"

    // Example
    const val ExampleTitle = "Example screen"
    const val ExampleHeadline = "Reusable UI template"
    const val ExampleDescription = "This screen demonstrates how to compose app widgets with centralized constants."
    const val ExamplePrimaryAction = "Primary action"
    const val ExampleSecondaryAction = "Secondary action"

    fun withValue(prefix: String, value: String): String = "$prefix: $value"
}
