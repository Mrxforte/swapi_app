package com.example.swapi.presentation.people

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import com.example.swapi.presentation.designsystem.components.LoadingStateView
import com.example.swapi.presentation.designsystem.components.MessageStateView
import com.example.swapi.presentation.designsystem.components.CatalogItemCard
import com.example.swapi.presentation.theme.CloudWhite
import com.example.swapi.presentation.theme.NebulaBlue
import com.example.swapi.presentation.theme.StarDust
import com.example.swapi.presentation.theme.SubtleGray

/** Tab content for the People section — no Scaffold, receives padding from MainScreen. */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PeopleTabContent(
    viewModel: PeopleViewModel,
    onPersonClick: (String) -> Unit,
    padding: PaddingValues
) {
    val state by viewModel.uiState.collectAsState()
    val filtered = state.people.filter { it.name.contains(state.searchQuery, ignoreCase = true) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = { viewModel.refresh(isPullRefresh = true) }
    )

    when {
        state.isLoading && state.people.isEmpty() -> LoadingStateView(Modifier.padding(padding))
        state.errorMessage != null -> MessageStateView(state.errorMessage!!, Modifier.padding(padding), Dimens.ScreenPaddingLarge)
        else -> Box(Modifier.fillMaxSize().padding(padding).pullRefresh(pullRefreshState)) {
            AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(Dimens.ItemPadding),
                    verticalArrangement = Arrangement.spacedBy(Dimens.SpacingMedium)
                ) {
                    item {
                        OutlinedTextField(
                            value = state.searchQuery, onValueChange = viewModel::onSearchQueryChange,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text(AppStrings.SearchCharactersPlaceholder) }, singleLine = true,
                            shape = RoundedCornerShape(28.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedContainerColor = CloudWhite, focusedContainerColor = CloudWhite,
                                unfocusedBorderColor = StarDust, focusedBorderColor = NebulaBlue, cursorColor = NebulaBlue
                            )
                        )
                    }
                    if (filtered.isEmpty()) {
                        item { MessageStateView(AppStrings.NoCharactersForQuery, padding = Dimens.ScreenPaddingLarge) }
                    }
                    items(filtered, key = { it.id }) { person ->
                        CatalogItemCard(
                            title = person.name,
                            subtitle = AppStrings.HeightMassHairEyesTemplate.format(
                                person.height,
                                person.mass,
                                person.hairColor,
                                person.eyeColor
                            ),
                            onClick = { onPersonClick(person.id) }
                        )
                    }
                }
            }
            PullRefreshIndicator(state.isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}

// Legacy alias kept so existing NavHost references still compile during migration
@Composable
fun PeopleScreen(
    viewModel: PeopleViewModel,
    onOpenDetails: (String) -> Unit,
    onOpenSettings: () -> Unit = {},
    onOpenProfile: () -> Unit = {}
) {
    PeopleTabContent(viewModel = viewModel, onPersonClick = onOpenDetails, padding = PaddingValues())
}
