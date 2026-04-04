package com.example.swapi.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swapi.core.constants.AppDefaults
import com.example.swapi.data.preferences.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val preferences: UserPreferencesRepository
) : ViewModel() {

    val profileName = preferences.profileName.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AppDefaults.ProfileName
    )

    fun saveProfileName(name: String) {
        viewModelScope.launch {
            preferences.setProfileName(name)
        }
    }
}
