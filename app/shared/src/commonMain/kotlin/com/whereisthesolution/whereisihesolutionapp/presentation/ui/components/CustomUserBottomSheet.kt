package com.whereisthesolution.whereisihesolutionapp.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.whereisthesolution.whereisihesolutionapp.presentation.ui.screens.login.LoginUiState
import org.jetbrains.compose.resources.painterResource
import whereisthesolution.app.shared.generated.resources.Res
import whereisthesolution.app.shared.generated.resources.icon_invisible
import whereisthesolution.app.shared.generated.resources.icon_visible

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomUserBottomSheet(
    onDismissRequest: () -> Unit,
    isLoading: Boolean,
    onSubmit: () -> Unit,
    uiState: LoginUiState,
    name: (name: String) -> Unit,
    email: (email: String) -> Unit,
    password: (password: String) -> Unit,
    confirmPassword: (confirmPassword: String) -> Unit
) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var passwordVisible by remember { mutableStateOf(false) }
    var passwordConfirmVisible by remember { mutableStateOf(false) }

    val cities = remember { listOf("Juazeiro do Norte", "Crato", "Barbalha") }
    var selectedCity by remember { mutableStateOf(cities.first()) }
    var isCityMenuExpanded by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Novo Cadastro",
                style = MaterialTheme.typography.headlineSmall
            )

            HorizontalDivider(modifier = Modifier.fillMaxWidth())

            OutlinedTextField(
                value = uiState.name,
                onValueChange = { name(it) },
                label = { Text("Nome Completo") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = uiState.nameRegisterError != null,
                supportingText = {
                    uiState.nameRegisterError?.let { error ->
                        Text(text = error)
                    }
                },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color(0xFF1769AA),
                    focusedBorderColor = Color(0xFF1769AA)
                )
            )

            ExposedDropdownMenuBox(
                expanded = isCityMenuExpanded,
                onExpandedChange = { isCityMenuExpanded = !isCityMenuExpanded }
            ) {
                OutlinedTextField(
                    value = selectedCity,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Cidade") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCityMenuExpanded)
                    },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                        focusedLabelColor = Color(0xFF1769AA),
                        focusedBorderColor = Color(0xFF1769AA)
                    ),
                    modifier = Modifier
                        .menuAnchor() // Vincula o dropdown ao campo
                        .fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),

                    )

                ExposedDropdownMenu(
                    expanded = isCityMenuExpanded,
                    onDismissRequest = { isCityMenuExpanded = false }
                ) {
                    cities.forEach { city ->
                        DropdownMenuItem(
                            text = { Text(city) },
                            onClick = {
                                selectedCity = city
                                isCityMenuExpanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }

            OutlinedTextField(
                value = uiState.email,
                onValueChange = { email(it) },
                label = { Text("E-mail") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = uiState.emailRegisterError != null,
                placeholder = {
                    Text("seu@email.com")
                },
                supportingText = {
                    uiState.emailRegisterError?.let { error ->
                        Text(text = error)
                    }
                },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color(0xFF1769AA),
                    focusedBorderColor = Color(0xFF1769AA)
                )

            )

            OutlinedTextField(
                value = uiState.password,
                onValueChange = { password(it) },
                label = { Text("Senha") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            passwordVisible = !passwordVisible
                        }
                    ) {
                        Icon(
                            painter = if (passwordVisible) {
                                painterResource(Res.drawable.icon_invisible)
                            } else {
                                painterResource(Res.drawable.icon_visible)
                            },
                            contentDescription =
                                if (passwordVisible)
                                    "Ocultar senha"
                                else
                                    "Mostrar senha",
                            modifier = Modifier
                                .size(16.dp)
                        )
                    }
                },
                isError = uiState.passwordRegisterError != null,
                supportingText = {
                    uiState.passwordRegisterError?.let { error ->
                        Text(text = error)
                    }
                },
                visualTransformation =
                    if (passwordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color(0xFF1769AA),
                    focusedBorderColor = Color(0xFF1769AA)
                )
            )

            OutlinedTextField(
                value = uiState.passwordConfirm,
                onValueChange = { confirmPassword(it) },
                label = { Text("Confirme sua senha") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            passwordConfirmVisible = !passwordConfirmVisible
                        }
                    ) {
                        Icon(
                            painter = if (passwordConfirmVisible) {
                                painterResource(Res.drawable.icon_invisible)
                            } else {
                                painterResource(Res.drawable.icon_visible)
                            },
                            contentDescription =
                                if (passwordConfirmVisible)
                                    "Ocultar senha"
                                else
                                    "Mostrar senha",
                            modifier = Modifier
                                .size(16.dp)
                        )
                    }
                },
                isError = uiState.passwordConfirmRegisterError != null,
                supportingText = {
                    uiState.passwordConfirmRegisterError?.let { error ->
                        Text(text = error)
                    }
                },
                visualTransformation =
                    if (passwordConfirmVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color(0xFF1769AA),
                    focusedBorderColor = Color(0xFF1769AA)
                )
            )

            Button(
                onClick = {
                    onSubmit()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = uiState.name.isNotBlank() &&
                        uiState.email.isNotBlank() &&
                        uiState.password.isNotBlank() &&
                        uiState.passwordConfirm.isNotBlank()
                        && !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1769AA)
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Cadastrar")
                }
            }
        }
    }

}