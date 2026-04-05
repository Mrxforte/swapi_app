package com.example.swapi.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.swapi.presentation.designsystem.AppStrings
import com.example.swapi.presentation.designsystem.Dimens
import com.example.swapi.presentation.designsystem.components.AppScreenScaffold
import com.example.swapi.presentation.designsystem.components.DetailHeroCard
import com.example.swapi.presentation.designsystem.components.DetailInfoCard
import com.example.swapi.presentation.designsystem.components.DetailMessageCard
import com.example.swapi.presentation.designsystem.components.DetailSectionTitle
import com.example.swapi.presentation.designsystem.components.LoadingStateView

@Composable
fun PersonDetailsScreen(
    viewModel: PersonDetailsViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val details = state.details

    AppScreenScaffold(
        title = details?.person?.name ?: AppStrings.DetailsTitle,
        onBack = onBack,
        centerTitle = true
    ) { padding ->
        when {
            state.isLoading -> {
                LoadingStateView(modifier = Modifier.padding(padding))
            }

            state.errorMessage != null -> {
                com.example.swapi.presentation.designsystem.components.MessageStateView(
                    message = state.errorMessage!!,
                    modifier = Modifier.padding(padding),
                    padding = Dimens.ScreenPaddingLarge
                )
            }

            details == null -> {
                com.example.swapi.presentation.designsystem.components.MessageStateView(
                    message = AppStrings.NoCharactersFound,
                    modifier = Modifier.padding(padding)
                )
            }

            else -> {
                val character = details.person
                val speciesItems = details.species
                val filmItems = details.films

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(Dimens.ScreenPadding),
                    verticalArrangement = Arrangement.spacedBy(Dimens.SpacingMedium)
                ) {
                    item {
                        DetailHeroCard(
                            title = character.name,
                            subtitle = "${AppStrings.BirthYearPrefix}: ${character.birthYear}  ·  ${AppStrings.GenderPrefix}: ${character.gender}"
                        )
                    }

                    item { DetailSectionTitle(AppStrings.BasicInformation) }
                    item { DetailInfoCard(AppStrings.BirthYearPrefix, character.birthYear) }
                    item { DetailInfoCard(AppStrings.HeightPrefix, character.height) }
                    item { DetailInfoCard(AppStrings.MassPrefix, character.mass) }
                    item { DetailInfoCard(AppStrings.GenderPrefix, character.gender) }

                    item { DetailSectionTitle(AppStrings.SpeciesTitle) }
                    if (speciesItems.isEmpty()) {
                        item { DetailMessageCard(AppStrings.NoSpeciesAvailable) }
                    } else {
                        items(speciesItems) { speciesName ->
                            DetailInfoCard(label = AppStrings.SpeciesTitle, value = speciesName)
                        }
                    }

                    item { DetailSectionTitle(AppStrings.FilmsTitle) }
                    if (filmItems.isEmpty()) {
                        item { DetailMessageCard(AppStrings.NoFilmsAvailable) }
                    } else {
                        items(filmItems, key = { it.id }) { film ->
                            DetailInfoCard(label = film.title, value = film.openingCrawl)
                        }
                    }
                }
            }
        }
    }
}
