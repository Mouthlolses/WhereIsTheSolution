package com.whereisthesolution.whereisihesolutionapp.presentation.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whereisthesolution.whereisihesolutionapp.data.entity.UserEntity
import com.whereisthesolution.whereisihesolutionapp.network.dto.RegisterUserRequest
import com.whereisthesolution.whereisihesolutionapp.repository.UserRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class LoginViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _uiEvent = Channel<LoginUiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    fun registerUser(userRequest: RegisterUserRequest) {
        viewModelScope.launch {
            try {
                repository.registerUser(userRequest)
                    .onSuccess {
                        _uiEvent.send(LoginUiEvent.NavigateToHome)
                    }
                    .onFailure { e ->
                        _uiEvent.send(LoginUiEvent.ShowError(e.message))
                    }
            } catch (e: Exception) {
                _uiEvent.send(
                    LoginUiEvent.ShowError(e.message)
                )
            }
        }
    }

}


sealed interface LoginUiEvent {
    data object NavigateToHome : LoginUiEvent
    data class ShowError(val message: String?) : LoginUiEvent
}