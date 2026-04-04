package com.example.swapi.presentation.example

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.swapi.presentation.designsystem.AppStrings
import com.example.swapi.presentation.designsystem.Dimens
import com.example.swapi.presentation.designsystem.components.AppScreenScaffold

@Composable
fun ExampleScreen(
    onBack: () -> Unit
) {
    AppScreenScaffold(
        title = AppStrings.ExampleTitle,
        onBack = onBack
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Dimens.ScreenPadding),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingLarge)
        ) {
            Text(
                text = AppStrings.ExampleHeadline,
                style = MaterialTheme.typography.headlineSmall
            )

            Card(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = AppStrings.ExampleDescription,
                    modifier = Modifier.padding(Dimens.ItemPadding),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(AppStrings.ExamplePrimaryAction)
            }

            OutlinedButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(AppStrings.ExampleSecondaryAction)
            }
        }
    }
}
