package com.example.swapi.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swapi.data.preferences.UserPreferencesRepository
import com.example.swapi.presentation.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preferences: UserPreferencesRepository
) : ViewModel() {

    private val _nextDestination = MutableStateFlow<String?>(null)
    val nextDestination: StateFlow<String?> = _nextDestination.asStateFlow()

    init {
        viewModelScope.launch {
            // Small delay keeps splash visible long enough to feel intentional.
            delay(700)
            val done = preferences.onboardingCompleted.first()
            _nextDestination.value = if (done) Routes.People else Routes.Onboarding
        }
    }
}
