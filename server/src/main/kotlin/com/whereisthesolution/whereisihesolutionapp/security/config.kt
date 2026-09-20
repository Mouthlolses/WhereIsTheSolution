package com.whereisthesolution.whereisihesolutionapp.security

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.AuthenticationFailedCause
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.response.respond

fun Application.configureSecurity() {

    install(Authentication) {
        // Define a validação pelo cabeçalho HTTP "X-API-KEY"
        provider("api-key-auth") {
            authenticate { context ->
                val apiKeyHeader = context.call.request.headers["X-API-KEY"]
                val expectedKey =
                    System.getenv("API_SECRET_KEY") ?: "chave_padrao_desenvolvimento_local"

                if (apiKeyHeader == expectedKey) {
                    context.principal(UserIdPrincipal("app-authorized"))
                } else {
                    // Rejeita requisições sem a chave correta
                    context.challenge(
                        "api-key-auth",
                        AuthenticationFailedCause.InvalidCredentials
                    ) { challenge, call ->
                        call.respond(
                            HttpStatusCode.Unauthorized,
                            mapOf("error" to "Acesso não autorizado: X-API-KEY inválida ou ausente")
                        )
                        challenge.complete()
                    }
                }
            }
        }
    }
}