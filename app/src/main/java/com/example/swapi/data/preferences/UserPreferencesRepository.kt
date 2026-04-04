package com.example.swapi.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.swapi.core.constants.AppDefaults
import com.example.swapi.presentation.theme.ThemeMode
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

@Singleton
class UserPreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object Keys {
        val OnboardingCompleted = stringPreferencesKey("onboarding_completed")
        val ThemeMode = stringPreferencesKey("theme_mode")
        val ProfileName = stringPreferencesKey("profile_name")
    }

    val onboardingCompleted: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[Keys.OnboardingCompleted]?.toBoolean() ?: false
    }

    val themeMode: Flow<ThemeMode> = context.dataStore.data.map { prefs ->
        ThemeMode.entries.firstOrNull { it.name == prefs[Keys.ThemeMode] } ?: ThemeMode.SYSTEM
    }

    val profileName: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[Keys.ProfileName] ?: AppDefaults.ProfileName
    }

    suspend fun setOnboardingCompleted(isCompleted: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[Keys.OnboardingCompleted] = isCompleted.toString()
        }
    }

    suspend fun setThemeMode(mode: ThemeMode) {
        context.dataStore.edit { prefs ->
            prefs[Keys.ThemeMode] = mode.name
        }
    }

    suspend fun setProfileName(name: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.ProfileName] = name.ifBlank { AppDefaults.ProfileName }
        }
    }
}
