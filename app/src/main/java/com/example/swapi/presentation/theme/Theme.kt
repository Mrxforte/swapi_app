package com.example.swapi.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = NebulaBlue,
    secondary = SolarGold,
    background = CloudWhite,
    surface = StarDust,
    outline = CardOutline,
    onPrimary = CloudWhite,
    onSecondary = DeepSpace,
    onBackground = DeepSpace,
    onSurface = DeepSpace
)

private val DarkColors = darkColorScheme(
    primary = SolarGold,
    secondary = NebulaBlue,
    background = DeepSpace,
    surface = DarkSurface,
    outline = MeteorGray,
    onPrimary = DeepSpace,
    onSecondary = CloudWhite,
    onBackground = CloudWhite,
    onSurface = CloudWhite
)

@Composable
fun SwapiTheme(
    themeMode: ThemeMode,
    content: @Composable () -> Unit
) {
    val isDark = when (themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    MaterialTheme(
        colorScheme = if (isDark) DarkColors else LightColors,
        typography = MaterialTheme.typography,
        content = content
    )
}
