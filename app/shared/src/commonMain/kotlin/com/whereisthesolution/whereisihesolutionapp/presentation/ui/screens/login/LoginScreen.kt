package com.whereisthesolution.whereisihesolutionapp.presentation.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whereisthesolution.whereisihesolutionapp.presentation.ui.components.CustomUserBottomSheet
import org.jetbrains.compose.resources.painterResource
import whereisthesolution.app.shared.generated.resources.Res
import whereisthesolution.app.shared.generated.resources.icon_invisible
import whereisthesolution.app.shared.generated.resources.icon_visible

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    onRegisterUser: (name: String, email: String) -> Unit = { name, email ->

    },
    onForgotPasswordClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var showCreateUserSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .navigationBarsPadding(),
        containerColor = Color(0xFFF7F9FC),
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->

        if (showCreateUserSheet) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                CustomUserBottomSheet(
                    onDismissRequest = { showCreateUserSheet = false },
                    onSubmit = { name, email ->
                        onRegisterUser(name, email)
                    }
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            // Decoração superior
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(
                        color = Color(0xFF1769AA),
                        shape = RoundedCornerShape(
                            bottomStart = 48.dp,
                            bottomEnd = 48.dp
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(82.dp))


                Text(
                    text = "Voz Cidadã",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )

                Text(
                    text = "Participe. Colabore. Transforme.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.85f)
                )

                Spacer(modifier = Modifier.height(55.dp))

                // Card principal
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Bem-vindo",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = Color(0xFF172B4D)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Entre para acompanhar e participar das melhorias da sua cidade.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF687386)
                        )

                        Spacer(modifier = Modifier.height(26.dp))

                        // Email
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            label = {
                                Text("E-mail")
                            },
                            placeholder = {
                                Text("seu@email.com")
                            },
                            shape = RoundedCornerShape(16.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            label = {
                                Text("Senha")
                            },
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
                            visualTransformation =
                                if (passwordVisible) {
                                    VisualTransformation.None
                                } else {
                                    PasswordVisualTransformation()
                                },
                            shape = RoundedCornerShape(16.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Esqueci minha senha
                        Text(
                            text = "Esqueci minha senha",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onForgotPasswordClick()
                                },
                            textAlign = TextAlign.End,
                            style = MaterialTheme.typography.labelLarge,
                            color = Color(0xFF1769AA),
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Botão entrar
                        Button(
                            onClick = onLoginClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(28.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1769AA)
                            )
                        ) {
                            Text(
                                text = "Entrar",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(22.dp))

                        // Divisor
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            HorizontalDivider(
                                modifier = Modifier.weight(1f),
                                color = Color(0xFFE1E5EA)
                            )

                            Text(
                                text = "  ou  ",
                                color = Color(0xFF8A94A6),
                                style = MaterialTheme.typography.bodySmall
                            )

                            HorizontalDivider(
                                modifier = Modifier.weight(1f),
                                color = Color(0xFFE1E5EA)
                            )
                        }

                        Spacer(modifier = Modifier.height(22.dp))

                        OutlinedButton(
                            onClick = {
                                showCreateUserSheet = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(28.dp)
                        ) {
                            Text(
                                text = "Criar uma conta",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Construindo uma cidade melhor juntos",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF7B8798)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}


@Preview(
    showBackground = true
)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onLoginClick = {},
        onRegisterUser = { name, email ->

        },
        onForgotPasswordClick = {}
    )
}