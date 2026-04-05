package com.example.swapi.presentation.starships

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.swapi.presentation.designsystem.AppStrings
import com.example.swapi.presentation.designsystem.Dimens
import com.example.swapi.presentation.designsystem.components.AppScreenScaffold
import com.example.swapi.presentation.designsystem.components.CatalogItemCard
import com.example.swapi.presentation.designsystem.components.DetailHeroCard
import com.example.swapi.presentation.designsystem.components.DetailInfoCard
import com.example.swapi.presentation.designsystem.components.LoadingStateView
import com.example.swapi.presentation.designsystem.components.MessageStateView
import com.example.swapi.presentation.theme.CloudWhite
import com.example.swapi.presentation.theme.NebulaBlue
import com.example.swapi.presentation.theme.StarDust
import com.example.swapi.presentation.theme.SubtleGray

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StarshipsTabContent(
    viewModel: StarshipsViewModel,
    onStarshipClick: (String) -> Unit,
    padding: PaddingValues
) {
    val state: StarshipsUiState by viewModel.uiState.collectAsState()
    val filtered = state.starships.filter { it.name.contains(state.searchQuery, ignoreCase = true) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = { viewModel.refresh(pull = true) }
    )

    when {
        state.isLoading && state.starships.isEmpty() -> LoadingStateView(Modifier.padding(padding))
        state.errorMessage != null -> MessageStateView(state.errorMessage!!, Modifier.padding(padding), Dimens.ScreenPaddingLarge)
        else -> Box(Modifier.fillMaxSize().padding(padding).pullRefresh(pullRefreshState)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(Dimens.ItemPadding),
                verticalArrangement = Arrangement.spacedBy(Dimens.SpacingMedium)
            ) {
                item {
                    OutlinedTextField(
                        value = state.searchQuery,
                        onValueChange = viewModel::onSearchQueryChange,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(AppStrings.SearchPlaceholder, color = SubtleGray) },
                        singleLine = true,
                        shape = RoundedCornerShape(28.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = CloudWhite,
                            focusedContainerColor = CloudWhite,
                            unfocusedBorderColor = StarDust,
                            focusedBorderColor = NebulaBlue,
                            cursorColor = NebulaBlue
                        )
                    )
                }
                if (filtered.isEmpty()) {
                    item { MessageStateView(AppStrings.NoResultsForQuery, padding = Dimens.ScreenPaddingLarge) }
                }
                items(filtered, key = { it.id }) { item ->
                    CatalogItemCard(
                        title = item.name,
                        subtitle = AppStrings.withValue(AppStrings.ModelPrefix, item.model),
                        onClick = { onStarshipClick(item.id) }
                    )
                }
            }
            PullRefreshIndicator(state.isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}

@Composable
fun StarshipDetailsScreen(
    viewModel: StarshipsDetailsViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val item = state.item

    AppScreenScaffold(title = item?.name ?: AppStrings.StarshipsTab, onBack = onBack, centerTitle = true) { padding ->
        when {
            state.isLoading -> LoadingStateView(Modifier.padding(padding))
            state.errorMessage != null -> MessageStateView(state.errorMessage!!, Modifier.padding(padding), Dimens.ScreenPaddingLarge)
            item == null -> MessageStateView(AppStrings.NoResultsFound, Modifier.padding(padding))
            else -> LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(Dimens.ScreenPadding),
                verticalArrangement = Arrangement.spacedBy(Dimens.SpacingMedium)
            ) {
                item {
                    DetailHeroCard(
                        title = item.name,
                        subtitle = "${AppStrings.ModelPrefix}: ${item.model}  ·  ${AppStrings.StarshipClassPrefix}: ${item.starshipClass}"
                    )
                }
                item { DetailInfoCard(AppStrings.NamePrefix, item.name) }
                item { DetailInfoCard(AppStrings.ModelPrefix, item.model) }
                item { DetailInfoCard(AppStrings.ManufacturerPrefix, item.manufacturer) }
                item { DetailInfoCard(AppStrings.StarshipClassPrefix, item.starshipClass) }
                item { DetailInfoCard(AppStrings.CostInCreditsPrefix, item.costInCredits) }
                item { DetailInfoCard(AppStrings.LengthPrefix, item.length) }
                item { DetailInfoCard(AppStrings.MaxSpeedPrefix, item.maxAtmospheringSpeed) }
                item { DetailInfoCard(AppStrings.CrewPrefix, item.crew) }
                item { DetailInfoCard(AppStrings.PassengersPrefix, item.passengers) }
                item { DetailInfoCard(AppStrings.CargoCapacityPrefix, item.cargoCapacity) }
                item { DetailInfoCard(AppStrings.ConsumablesPrefix, item.consumables) }
                item { DetailInfoCard(AppStrings.HyperdriveRatingPrefix, item.hyperdriveRating) }
                item { DetailInfoCard(AppStrings.MGLTPrefix, item.mglt) }
            }
        }
    }
}

