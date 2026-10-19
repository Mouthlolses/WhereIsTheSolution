package com.whereisthesolution.whereisihesolutionapp.network

import com.whereisthesolution.whereisihesolutionapp.network.keys.ApiSecretKey
import com.whereisthesolution.whereisihesolutionapp.session.SessionManager
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


fun createHttpClient(
    sessionManager: SessionManager
): HttpClient {
    return HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    encodeDefaults = true
                }
            )
        }

        install(Auth) {
            bearer {
                loadTokens {
                    sessionManager.getToken()?.let { token ->
                        BearerTokens(
                            accessToken = token,
                            refreshToken = ""
                        )
                    }
                }
            }
        }

        defaultRequest {
            header(
                key = ApiSecretKey.API_SECRET_KEY_KEY,
                value = ApiSecretKey.API_SECRET_KEY_VALUE
            )
        }
    }
}