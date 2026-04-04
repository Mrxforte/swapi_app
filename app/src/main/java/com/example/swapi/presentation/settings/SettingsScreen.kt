package com.example.swapi.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.swapi.presentation.designsystem.AppStrings
import com.example.swapi.presentation.designsystem.Dimens
import com.example.swapi.presentation.designsystem.components.AppScreenScaffold
import com.example.swapi.presentation.theme.ThemeMode

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onBack: () -> Unit
) {
    val selectedMode by viewModel.themeMode.collectAsState()

    AppScreenScaffold(
        title = AppStrings.SettingsTitle,
        onBack = onBack
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(Dimens.ScreenPadding),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingLarge)
        ) {
            Text(AppStrings.Theme)
            ThemeOption(
                label = AppStrings.ThemeSystem,
                isSelected = selectedMode == ThemeMode.SYSTEM,
                onSelect = { viewModel.onThemeChanged(ThemeMode.SYSTEM) }
            )
            ThemeOption(
                label = AppStrings.ThemeLight,
                isSelected = selectedMode == ThemeMode.LIGHT,
                onSelect = { viewModel.onThemeChanged(ThemeMode.LIGHT) }
            )
            ThemeOption(
                label = AppStrings.ThemeDark,
                isSelected = selectedMode == ThemeMode.DARK,
                onSelect = { viewModel.onThemeChanged(ThemeMode.DARK) }
            )
        }
    }
}

@Composable
private fun ThemeOption(
    label: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = onSelect)
        Text(text = label)
    }
}
