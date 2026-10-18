package com.whereisthesolution.whereisihesolutionapp.presentation.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whereisthesolution.whereisihesolutionapp.network.dto.AuthUserRequest
import com.whereisthesolution.whereisihesolutionapp.network.dto.RegisterUserRequest
import com.whereisthesolution.whereisihesolutionapp.repository.UserRepository
import com.whereisthesolution.whereisihesolutionapp.utils.isValidEmail
import com.whereisthesolution.whereisihesolutionapp.utils.validateFields
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
                name = name,
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

    fun onPasswordConfirmChanged(passwordConfirm: String) {
        _uiState.update {
            it.copy(
                passwordConfirm = passwordConfirm,
                passwordConfirmRegisterError = null
            )
        }
    }


    fun registerUser() {

        val state = _uiState.value

        val name = state.name.trim()
        val email = state.email.trim()

        if (name.isEmpty()) {
            _uiState.update { it.copy(nameRegisterError = "O nome é obrigatório") }
            return
        }

        if (email.isEmpty()) {
            _uiState.update { it.copy(emailRegisterError = "O email é obrigatório") }
            return
        }

        if (_uiState.value.password.isEmpty()) {
            _uiState.update { it.copy(passwordRegisterError = "A senha é obrigatória") }
            return
        }

        if (_uiState.value.password != _uiState.value.passwordConfirm) {
            _uiState.update {
                it.copy(
                    passwordRegisterError = "As senhas não coincidem",
                    passwordConfirmRegisterError = "As senhas não coincidem"
                )
            }
            return
        }

        if (!isValidEmail(_uiState.value.email)) {
            _uiState.update { it.copy(emailRegisterError = "O email é inválido") }
            return
        }

        val request = RegisterUserRequest(
            name = name,
            email = email,
            password = state.password
        )

        viewModelScope.launch {
            _uiState.update { it.copy(loading = true) }
            try {
                repository.registerUser(request)
                    .onSuccess {
                        _uiState.update { it.copy(loading = false) }
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

    fun loginUser() {

        val state = _uiState.value

        val email = state.email.trim()
        val password = state.password

        val request = AuthUserRequest(
            email = email,
            password = password
        )

        if (!validateFields(request, _uiState)) return


        viewModelScope.launch {
            _uiState.update { it.copy(loading = true) }
            try {
                repository.authenticateUser(request)
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

}


data class LoginUiState(
    val loading: Boolean = false,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val passwordConfirm: String = "",

    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,

    val nameRegisterError: String? = null,
    val emailRegisterError: String? = null,
    val passwordRegisterError: String? = null,
    val passwordConfirmRegisterError: String? = null
)


sealed interface LoginUiEvent {
    data object NavigateToHome : LoginUiEvent
    data class ShowError(val message: String?) : LoginUiEvent
}

