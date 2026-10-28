package com.whereisthesolution.whereisihesolutionapp.presentation.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.FeedPostResponse
import com.whereisthesolution.whereisihesolutionapp.domain.model.user.User
import com.whereisthesolution.whereisihesolutionapp.repository.UserRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class HomeViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<HomeUiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        observeUser()
    }

    private fun observeUser() {
        repository
            .observeLoggedUser()
            .onEach { user ->
                _uiState.update {
                    it.copy(
                        user = user,
                    )
                }
            }
            .catch { e ->
                _uiEvent.send(HomeUiEvent.ShowError(e.message))
            }
            .launchIn(
                viewModelScope
            )
    }

    fun refreshPosts() {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true) }
            try {
                val posts = repository.getFeedPosts(page = 1, pageSize = 20)
                _uiState.update {
                    it.copy(
                        posts = posts,
                        loading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(loading = false)
                }
                _uiEvent.send(HomeUiEvent.ShowError(e.message))
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                repository.logout()
                _uiEvent.send(
                    HomeUiEvent.NavigateToLogin
                )
            } catch (e: Exception) {
                _uiEvent.send(
                    HomeUiEvent.ShowError(e.message)
                )
            }
        }
    }
}


data class HomeUiState(
    val user: User? = null,
    val posts: List<FeedPostResponse> = listOf(),
    val loading: Boolean = true,
)


sealed interface HomeUiEvent {
    data object NavigateToLogin : HomeUiEvent
    data class ShowError(val message: String?) : HomeUiEvent
}
