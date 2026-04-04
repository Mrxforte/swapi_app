package com.example.swapi.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swapi.data.preferences.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val preferences: UserPreferencesRepository
) : ViewModel() {

    fun completeOnboarding() {
        viewModelScope.launch {
            preferences.setOnboardingCompleted(true)
        }
    }
}
