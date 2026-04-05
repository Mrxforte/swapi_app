package com.example.swapi.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.swapi.presentation.details.PersonDetailsScreen
import com.example.swapi.presentation.example.ExampleScreen
import com.example.swapi.presentation.films.FilmDetailsScreen
import com.example.swapi.presentation.main.MainScreen
import com.example.swapi.presentation.onboarding.OnboardingScreen
import com.example.swapi.presentation.planets.PlanetDetailsScreen
import com.example.swapi.presentation.profile.ProfileScreen
import com.example.swapi.presentation.settings.SettingsScreen
import com.example.swapi.presentation.species.SpeciesDetailsScreen
import com.example.swapi.presentation.splash.SplashScreen
import com.example.swapi.presentation.starships.StarshipDetailsScreen
import com.example.swapi.presentation.vehicles.VehicleDetailsScreen

@Composable
fun SwapiNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Splash) {
        composable(Routes.Splash) {
            SplashScreen(
                viewModel = hiltViewModel(),
                onNavigateNext = { destination ->
                    navController.navigate(destination) {
                        popUpTo(Routes.Splash) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.Onboarding) {
            OnboardingScreen(
                viewModel = hiltViewModel(),
                onBack = { navController.popBackStack() },
                onFinish = {
                    navController.navigate(Routes.Main) {
                        popUpTo(Routes.Onboarding) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.Main) {
            MainScreen(
                onOpenSettings = { navController.navigate(Routes.Settings) },
                onOpenProfile = { navController.navigate(Routes.Profile) },
                onOpenPersonDetails = { navController.navigate(Routes.personDetails(it)) },
                onOpenFilmDetails = { navController.navigate(Routes.filmDetails(it)) },
                onOpenPlanetDetails = { navController.navigate(Routes.planetDetails(it)) },
                onOpenSpeciesDetails = { navController.navigate(Routes.speciesDetails(it)) },
                onOpenVehicleDetails = { navController.navigate(Routes.vehicleDetails(it)) },
                onOpenStarshipDetails = { navController.navigate(Routes.starshipDetails(it)) }
            )
        }

        composable(
            route = Routes.PersonDetails,
            arguments = listOf(navArgument(NavArgs.PersonId) { type = NavType.StringType })
        ) {
            PersonDetailsScreen(viewModel = hiltViewModel(), onBack = { navController.popBackStack() })
        }

        composable(
            route = Routes.FilmDetails,
            arguments = listOf(navArgument(NavArgs.FilmId) { type = NavType.StringType })
        ) {
            FilmDetailsScreen(viewModel = hiltViewModel(), onBack = { navController.popBackStack() })
        }

        composable(
            route = Routes.PlanetDetails,
            arguments = listOf(navArgument(NavArgs.PlanetId) { type = NavType.StringType })
        ) {
            PlanetDetailsScreen(viewModel = hiltViewModel(), onBack = { navController.popBackStack() })
        }

        composable(
            route = Routes.SpeciesDetails,
            arguments = listOf(navArgument(NavArgs.SpeciesId) { type = NavType.StringType })
        ) {
            SpeciesDetailsScreen(viewModel = hiltViewModel(), onBack = { navController.popBackStack() })
        }

        composable(
            route = Routes.VehicleDetails,
            arguments = listOf(navArgument(NavArgs.VehicleId) { type = NavType.StringType })
        ) {
            VehicleDetailsScreen(viewModel = hiltViewModel(), onBack = { navController.popBackStack() })
        }

        composable(
            route = Routes.StarshipDetails,
            arguments = listOf(navArgument(NavArgs.StarshipId) { type = NavType.StringType })
        ) {
            StarshipDetailsScreen(viewModel = hiltViewModel(), onBack = { navController.popBackStack() })
        }

        composable(Routes.Settings) {
            SettingsScreen(viewModel = hiltViewModel(), onBack = { navController.popBackStack() })
        }

        composable(Routes.Profile) {
            ProfileScreen(viewModel = hiltViewModel(), onBack = { navController.popBackStack() })
        }

        composable(Routes.Example) {
            ExampleScreen(onBack = { navController.popBackStack() })
        }
    }
}
