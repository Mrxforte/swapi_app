package com.example.swapi.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.swapi.presentation.designsystem.AppStrings
import com.example.swapi.presentation.designsystem.Dimens
import com.example.swapi.presentation.designsystem.components.AppScreenScaffold

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onBack: () -> Unit
) {
    val profileName by viewModel.profileName.collectAsState()
    var input by remember { mutableStateOf("") }

    LaunchedEffect(profileName) {
        input = profileName
    }

    AppScreenScaffold(
        title = AppStrings.ProfileTitle,
        onBack = onBack
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(Dimens.ScreenPadding),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingLarge)
        ) {
            Text(AppStrings.DisplayName)
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Button(
                onClick = {
                    viewModel.saveProfileName(input)
                    onBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(AppStrings.Save)
            }
        }
    }
}
