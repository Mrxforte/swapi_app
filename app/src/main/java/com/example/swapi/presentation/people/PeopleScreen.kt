package com.example.swapi.presentation.people

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.swapi.presentation.designsystem.AppIcons
import com.example.swapi.presentation.designsystem.AppStrings
import com.example.swapi.presentation.designsystem.Dimens
import com.example.swapi.presentation.designsystem.components.AppTopBarIconAction
import com.example.swapi.presentation.designsystem.components.AppScreenScaffold
import com.example.swapi.presentation.designsystem.components.LoadingStateView
import com.example.swapi.presentation.designsystem.components.MessageStateView

@Composable
fun PeopleScreen(
    viewModel: PeopleViewModel,
    onOpenDetails: (String) -> Unit,
    onOpenSettings: () -> Unit,
    onOpenProfile: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val filteredPeople = state.people.filter { person ->
        person.name.contains(state.searchQuery, ignoreCase = true)
    }

    AppScreenScaffold(
        title = AppStrings.StarWarsCharactersTitle,
        actions = {
            AppTopBarIconAction(
                icon = AppIcons.Profile,
                contentDescription = AppStrings.Profile,
                onClick = onOpenProfile
            )
            AppTopBarIconAction(
                icon = AppIcons.Settings,
                contentDescription = AppStrings.Settings,
                onClick = onOpenSettings
            )
        }
    ) { padding ->
        when {
            state.isLoading -> {
                LoadingStateView(modifier = Modifier.padding(padding))
            }

            state.errorMessage != null -> {
                MessageStateView(
                    message = state.errorMessage!!,
                    modifier = Modifier.padding(padding),
                    padding = Dimens.ScreenPaddingLarge
                )
            }

            state.people.isEmpty() -> {
                MessageStateView(
                    message = AppStrings.NoCharactersFound,
                    modifier = Modifier.padding(padding)
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentPadding = PaddingValues(Dimens.ItemPadding),
                    verticalArrangement = Arrangement.spacedBy(Dimens.SpacingMedium)
                ) {
                    item {
                        OutlinedTextField(
                            value = state.searchQuery,
                            onValueChange = viewModel::onSearchQueryChange,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text(AppStrings.SearchCharactersPlaceholder) },
                            singleLine = true
                        )
                    }

                    if (filteredPeople.isEmpty()) {
                        item {
                            MessageStateView(
                                message = AppStrings.NoCharactersForQuery,
                                padding = Dimens.ScreenPaddingLarge
                            )
                        }
                    }

                    items(filteredPeople, key = { it.id }) { person ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onOpenDetails(person.id) }
                        ) {
                            Column(modifier = Modifier.padding(Dimens.ItemPadding)) {
                                Text(person.name, style = MaterialTheme.typography.titleMedium)
                                Text(
                                    text = AppStrings.HeightMassHairEyesTemplate.format(
                                        person.height,
                                        person.mass,
                                        person.hairColor,
                                        person.eyeColor
                                    ),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
