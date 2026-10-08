package com.whereisthesolution.whereisihesolutionapp.utils

import com.whereisthesolution.whereisihesolutionapp.network.dto.AuthUserRequest
import com.whereisthesolution.whereisihesolutionapp.presentation.ui.screens.login.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update


fun isValidEmail(email: String): Boolean {
    return email.matches(
        Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    )
}

fun validateFields(
    request: AuthUserRequest,
    _uiState: MutableStateFlow<LoginUiState>
): Boolean {
    val emailError = when {
        request.email.isBlank() ->
            "O email é obrigatório"

        !isValidEmail(request.email) ->
            "E-mail inválido"

        else -> null
    }

    val passwordError = when {
        request.password.isBlank() ->
            "A senha é obrigatória"

        request.password.length < 6 ->
            "A senha precisa conter pelo menos 6 caracteres"

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