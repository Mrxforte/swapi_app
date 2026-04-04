package com.example.swapi.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swapi.domain.usecase.GetPersonDetailsUseCase
import com.example.swapi.domain.usecase.RefreshPersonDetailsUseCase
import com.example.swapi.presentation.designsystem.AppStrings
import com.example.swapi.presentation.navigation.NavArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getPersonDetailsUseCase: GetPersonDetailsUseCase,
    private val refreshPersonDetailsUseCase: RefreshPersonDetailsUseCase
) : ViewModel() {

    private val personId: String = checkNotNull(savedStateHandle[NavArgs.PersonId])

    private val _uiState = MutableStateFlow(PersonDetailsUiState())
    val uiState: StateFlow<PersonDetailsUiState> = _uiState.asStateFlow()

    init {
        observeDetails(getPersonDetailsUseCase)
        refreshDetails()
    }

    fun refreshDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = refreshPersonDetailsUseCase(personId)
            if (result.isFailure && _uiState.value.details == null) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = AppStrings.OfflineEmptyError
                    )
                }
            }
        }
    }

    private fun observeDetails(getPersonDetailsUseCase: GetPersonDetailsUseCase) {
        viewModelScope.launch {
            getPersonDetailsUseCase(personId).collect { details ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        details = details,
                        errorMessage = null
                    )
                }
            }
        }
    }
}
