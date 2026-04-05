package com.example.swapi.presentation.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swapi.domain.usecase.GetPeopleUseCase
import com.example.swapi.domain.usecase.RefreshPeopleUseCase
import com.example.swapi.presentation.designsystem.AppStrings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val getPeople: GetPeopleUseCase,
    private val refreshPeople: RefreshPeopleUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PeopleUiState())
    val uiState: StateFlow<PeopleUiState> = _uiState.asStateFlow()

    init {
        observePeople()
        refresh()
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun refresh(isPullRefresh: Boolean = false) {
        viewModelScope.launch {
            val hasCachedData = _uiState.value.people.isNotEmpty()
            _uiState.update {
                it.copy(
                    isLoading = !hasCachedData && !isPullRefresh,
                    isRefreshing = hasCachedData || isPullRefresh,
                    errorMessage = if (hasCachedData) null else it.errorMessage
                )
            }

            val result = refreshPeople()
            if (result.isFailure && _uiState.value.people.isEmpty()) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        errorMessage = AppStrings.OfflineEmptyError
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false
                    )
                }
            }
        }
    }

    private fun observePeople() {
        viewModelScope.launch {
            getPeople().collect { people ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        people = people,
                        errorMessage = null
                    )
                }
            }
        }
    }
}
