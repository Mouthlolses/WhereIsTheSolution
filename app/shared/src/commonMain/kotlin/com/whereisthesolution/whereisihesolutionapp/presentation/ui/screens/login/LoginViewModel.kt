package com.whereisthesolution.whereisihesolutionapp.presentation.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whereisthesolution.whereisihesolutionapp.network.dto.AuthUserRequest
import com.whereisthesolution.whereisihesolutionapp.network.dto.RegisterUserRequest
import com.whereisthesolution.whereisihesolutionapp.repository.UserRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LoginViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<LoginUiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onNameChanged(name: String) {
        _uiState.update {
            it.copy(
                email = name,
                nameError = null
            )
        }
    }


    fun onEmailChanged(email: String) {
        _uiState.update {
            it.copy(
                email = email,
                emailError = null
            )
        }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update {
            it.copy(
                password = password,
                passwordError = null
            )
        }
    }


    fun registerUser(userRequest: RegisterUserRequest) {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true) }
            try {
                repository.registerUser(userRequest)
                    .onSuccess {
                        _uiState.update { it.copy(loading = false) }
                        _uiEvent.send(LoginUiEvent.NavigateToHome)
                    }
                    .onFailure { e ->
                        _uiState.update { it.copy(loading = false) }
                        _uiEvent.send(LoginUiEvent.ShowError(e.message))
                    }
            } catch (e: Exception) {
                _uiState.update { it.copy(loading = false) }
                _uiEvent.send(
                    LoginUiEvent.ShowError(e.message)
                )
            } finally {
                _uiState.update {
                    it.copy(loading = false)
                }
            }
        }
    }

    fun loginUser(userRequest: AuthUserRequest) {
        val state = _uiState.value

        val request = AuthUserRequest(
            email = state.email,
            password = state.password
        )

        if (!validateFields(request)) return


        viewModelScope.launch {
            _uiState.update { it.copy(loading = true) }
            try {
                repository.authenticateUser(userRequest)
                    .onSuccess {
                        _uiState.update { it.copy(loading = false) }
                        _uiEvent.send(LoginUiEvent.NavigateToHome)
                    }
                    .onFailure { e ->
                        _uiState.update { it.copy(loading = false) }
                        _uiEvent.send(LoginUiEvent.ShowError(e.message))
                    }
            } catch (e: Exception) {
                _uiState.update { it.copy(loading = false) }
                _uiEvent.send(
                    LoginUiEvent.ShowError(e.message)
                )
            } finally {
                _uiState.update {
                    it.copy(loading = false)
                }
            }
        }

    }

    private fun isValidEmail(email: String): Boolean {
        return email.matches(
            Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
        )
    }

    private fun validateFields(request: AuthUserRequest): Boolean {
        val emailError = when {
            request.email.isBlank() ->
                "E-mail is required"

            !isValidEmail(request.email) ->
                "Invalid e-mail"

            else -> null
        }

        val passwordError = when {
            request.password.isBlank() ->
                "Password is required"

            request.password.length < 6 ->
                "Password must contain at least 6 characters"

            else -> null
        }

        _uiState.update {
            it.copy(
                emailError = emailError,
                passwordError = passwordError
            )
        }

        return emailError == null && passwordError == null
    }
}


data class LoginUiState(
    val loading: Boolean = false,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null
)


sealed interface LoginUiEvent {
    data object NavigateToHome : LoginUiEvent
    data class ShowError(val message: String?) : LoginUiEvent
}

