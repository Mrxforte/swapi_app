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
import com.example.swapi.presentation.onboarding.OnboardingScreen
import com.example.swapi.presentation.people.PeopleScreen
import com.example.swapi.presentation.profile.ProfileScreen
import com.example.swapi.presentation.settings.SettingsScreen
import com.example.swapi.presentation.splash.SplashScreen

@Composable
fun SwapiNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Splash
    ) {
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
                    navController.navigate(Routes.People) {
                        popUpTo(Routes.Onboarding) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.People) {
            PeopleScreen(
                viewModel = hiltViewModel(),
                onOpenDetails = { personId -> navController.navigate(Routes.details(personId)) },
                onOpenSettings = { navController.navigate(Routes.Settings) },
                onOpenProfile = { navController.navigate(Routes.Profile) }
            )
        }

        composable(
            route = Routes.Details,
            arguments = listOf(navArgument(NavArgs.PersonId) { type = NavType.StringType })
        ) {
            PersonDetailsScreen(
                viewModel = hiltViewModel(),
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.Settings) {
            SettingsScreen(
                viewModel = hiltViewModel(),
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.Profile) {
            ProfileScreen(
                viewModel = hiltViewModel(),
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.Example) {
            ExampleScreen(onBack = { navController.popBackStack() })
        }
    }
}
