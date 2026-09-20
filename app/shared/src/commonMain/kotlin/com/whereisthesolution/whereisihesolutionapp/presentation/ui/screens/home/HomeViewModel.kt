package com.whereisthesolution.whereisihesolutionapp.presentation.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whereisthesolution.whereisihesolutionapp.domain.model.user.User
import com.whereisthesolution.whereisihesolutionapp.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update


class HomeViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeUser()
    }

    private fun observeUser() {
        repository
            .observeLoggedUser()
            .onStart {
                _uiState.update {
                    it.copy(loading = true)
                }
            }
            .onEach { user ->
                _uiState.update {
                    it.copy(
                        user = user,
                        loading = false
                    )
                }
            }
            .launchIn(
                viewModelScope
            )
    }
}


data class HomeUiState(
    val user: User? = null,
    val loading: Boolean = false,
)


sealed interface HomeUiEvent {
    data object NavigateToHome : HomeUiEvent
    data class ShowError(val message: String?) : HomeUiEvent
}