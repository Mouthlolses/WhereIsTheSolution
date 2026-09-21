package com.whereisthesolution.whereisihesolutionapp.network

import com.whereisthesolution.whereisihesolutionapp.network.keys.ApiSecretKey
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


fun createHttpClient(): HttpClient {
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
        defaultRequest {
            header(
                key = ApiSecretKey.API_SECRET_KEY_KEY,
                value = ApiSecretKey.API_SECRET_KEY_VALUE
            )
        }
    }
}