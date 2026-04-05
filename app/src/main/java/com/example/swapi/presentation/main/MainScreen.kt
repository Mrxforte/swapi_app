package com.example.swapi.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.swapi.presentation.designsystem.AppIcons
import com.example.swapi.presentation.designsystem.AppStrings
import com.example.swapi.presentation.designsystem.components.AppScreenScaffold
import com.example.swapi.presentation.films.FilmsTabContent
import com.example.swapi.presentation.films.FilmsViewModel
import com.example.swapi.presentation.people.PeopleTabContent
import com.example.swapi.presentation.people.PeopleViewModel
import com.example.swapi.presentation.planets.PlanetsTabContent
import com.example.swapi.presentation.planets.PlanetsViewModel
import com.example.swapi.presentation.settings.SettingsViewModel
import com.example.swapi.presentation.species.SpeciesTabContent
import com.example.swapi.presentation.species.SpeciesViewModel
import com.example.swapi.presentation.starships.StarshipsTabContent
import com.example.swapi.presentation.starships.StarshipsViewModel
import com.example.swapi.presentation.theme.ThemeMode
import com.example.swapi.presentation.vehicles.VehiclesTabContent
import com.example.swapi.presentation.vehicles.VehiclesViewModel
import kotlinx.coroutines.launch

private val bottomNavTabs = listOf(MainTab.PEOPLE, MainTab.FILMS, MainTab.PLANETS)
private val drawerExtraTabs = listOf(MainTab.SPECIES, MainTab.VEHICLES, MainTab.STARSHIPS)

@Composable
fun MainScreen(
    onOpenSettings: () -> Unit,
    onOpenProfile: () -> Unit,
    onOpenPersonDetails: (String) -> Unit,
    onOpenFilmDetails: (String) -> Unit,
    onOpenPlanetDetails: (String) -> Unit,
    onOpenSpeciesDetails: (String) -> Unit,
    onOpenVehicleDetails: (String) -> Unit,
    onOpenStarshipDetails: (String) -> Unit
) {
    var tab by rememberSaveable { mutableStateOf(MainTab.PEOPLE) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val currentTheme by settingsViewModel.themeMode.collectAsState()
    val isDarkTheme = when (currentTheme) {
        ThemeMode.SYSTEM -> androidx.compose.foundation.isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val peopleViewModel: PeopleViewModel = hiltViewModel()
    val filmsViewModel: FilmsViewModel = hiltViewModel()
    val planetsViewModel: PlanetsViewModel = hiltViewModel()
    val speciesViewModel: SpeciesViewModel = hiltViewModel()
    val vehiclesViewModel: VehiclesViewModel = hiltViewModel()
    val starshipsViewModel: StarshipsViewModel = hiltViewModel()

    val title = when (tab) {
        MainTab.PEOPLE -> AppStrings.PeopleTab
        MainTab.FILMS -> AppStrings.FilmsTab
        MainTab.PLANETS -> AppStrings.PlanetsTab
        MainTab.SPECIES -> AppStrings.SpeciesTab
        MainTab.VEHICLES -> AppStrings.VehiclesTab
        MainTab.STARSHIPS -> AppStrings.StarshipsTab
    }

    val drawerContainerColor = if (isDarkTheme) {
        MaterialTheme.colorScheme.surface
    } else {
        MaterialTheme.colorScheme.background
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = drawerContainerColor,
                drawerShape = RoundedCornerShape(topEnd = 18.dp, bottomEnd = 18.dp)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(horizontal = 18.dp, vertical = 20.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = AppIcons.MainTab,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = AppStrings.AppName,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Text(
                                    text = "Galaxy companion",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }

                    DrawerSectionLabel(label = AppStrings.DrawerMainSection)
                    bottomNavTabs.forEach { item ->
                        DrawerNavItem(
                            label = labelFor(item),
                            icon = { Icon(iconFor(item), contentDescription = labelFor(item)) },
                            selected = tab == item,
                            onClick = {
                                tab = item
                                scope.launch { drawerState.close() }
                            }
                        )
                    }

                    DrawerSectionLabel(label = AppStrings.DrawerMoreSection)
                    drawerExtraTabs.forEach { item ->
                        DrawerNavItem(
                            label = labelFor(item),
                            icon = { Icon(iconFor(item), contentDescription = labelFor(item)) },
                            selected = tab == item,
                            onClick = {
                                tab = item
                                scope.launch { drawerState.close() }
                            }
                        )
                    }

                    DrawerSectionLabel(label = "Account")
                    DrawerNavItem(
                        label = AppStrings.Profile,
                        icon = { Icon(AppIcons.Profile, contentDescription = AppStrings.Profile) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            onOpenProfile()
                        }
                    )
                    DrawerNavItem(
                        label = AppStrings.Settings,
                        icon = { Icon(AppIcons.Settings, contentDescription = AppStrings.Settings) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            onOpenSettings()
                        }
                    )

                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "SWAPI Explorer 1.0",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
                        modifier = Modifier.padding(20.dp)
                    )
                }
            }
        }
    ) {
        AppScreenScaffold(
            title = title,
            navigationIcon = {
                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                    Icon(
                        imageVector = AppIcons.Menu,
                        contentDescription = AppStrings.DrawerNavigation,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 2.dp
                ) {
                    bottomNavTabs.forEach { item ->
                        NavigationBarItem(
                            selected = tab == item,
                            onClick = { tab = item },
                            icon = {
                                Icon(
                                    imageVector = iconFor(item),
                                    contentDescription = labelFor(item)
                                )
                            },
                            label = {
                                Text(
                                    text = labelFor(item),
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold)
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f),
                                unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
                            )
                        )
                    }
                }
            }
        ) { padding ->
            when (tab) {
                MainTab.PEOPLE -> PeopleTabContent(peopleViewModel, onOpenPersonDetails, padding)
                MainTab.FILMS -> FilmsTabContent(filmsViewModel, onOpenFilmDetails, padding)
                MainTab.PLANETS -> PlanetsTabContent(planetsViewModel, onOpenPlanetDetails, padding)
                MainTab.SPECIES -> SpeciesTabContent(speciesViewModel, onOpenSpeciesDetails, padding)
                MainTab.VEHICLES -> VehiclesTabContent(vehiclesViewModel, onOpenVehicleDetails, padding)
                MainTab.STARSHIPS -> StarshipsTabContent(starshipsViewModel, onOpenStarshipDetails, padding)
            }
        }
    }
}

@Composable
private fun DrawerSectionLabel(label: String) {
    Text(
        text = label.uppercase(),
        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
        modifier = Modifier.padding(start = 20.dp, top = 14.dp, bottom = 6.dp)
    )
}

@Composable
private fun DrawerNavItem(
    label: String,
    icon: @Composable () -> Unit,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                )
            )
        },
        selected = selected,
        icon = icon,
        onClick = onClick,
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f),
            unselectedContainerColor = MaterialTheme.colorScheme.surface,
            selectedIconColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.72f),
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedTextColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

private fun labelFor(tab: MainTab) = when (tab) {
    MainTab.PEOPLE -> AppStrings.PeopleTab
    MainTab.FILMS -> AppStrings.FilmsTab
    MainTab.PLANETS -> AppStrings.PlanetsTab
    MainTab.SPECIES -> AppStrings.SpeciesTab
    MainTab.VEHICLES -> AppStrings.VehiclesTab
    MainTab.STARSHIPS -> AppStrings.StarshipsTab
}

private fun iconFor(tab: MainTab) = when (tab) {
    MainTab.PEOPLE -> AppIcons.People
    MainTab.FILMS -> AppIcons.Films
    MainTab.PLANETS -> AppIcons.Planets
    MainTab.SPECIES -> AppIcons.Species
    MainTab.VEHICLES -> AppIcons.Vehicles
    MainTab.STARSHIPS -> AppIcons.Starships
}
