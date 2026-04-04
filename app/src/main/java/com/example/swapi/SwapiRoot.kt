package com.example.swapi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.swapi.presentation.navigation.SwapiNavHost
import com.example.swapi.presentation.settings.SettingsViewModel
import com.example.swapi.presentation.theme.SwapiTheme

@Composable
fun SwapiRoot(
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val themeMode by settingsViewModel.themeMode.collectAsState()

    SwapiTheme(themeMode = themeMode) {
        SwapiNavHost()
    }
}
