package com.whereisthesolution.whereisihesolutionapp.network.api

import com.whereisthesolution.whereisihesolutionapp.domain.model.user.User
import com.whereisthesolution.whereisihesolutionapp.domain.model.user.login.LoginResponse
import com.whereisthesolution.whereisihesolutionapp.network.config.NetworkConfig
import com.whereisthesolution.whereisihesolutionapp.network.dto.AuthUserRequest
import com.whereisthesolution.whereisihesolutionapp.network.dto.RegisterUserRequest
import com.whereisthesolution.whereisihesolutionapp.network.dto.UserResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType

class UserApi(
    private val client: HttpClient
) {
    suspend fun registerUser(
        request: RegisterUserRequest
    ): UserResponse {

        val response = client
            .post("${NetworkConfig.BASE_URL}/users") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }

        println("STATUS: ${response.status}")
        println("BODY: ${response.bodyAsText()}")

        return response.body()

    }

    suspend fun authenticateUser(
        request: AuthUserRequest
    ): LoginResponse {

        return client
            .post("${NetworkConfig.BASE_URL}/users/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            .body()
    }

    suspend fun getUsers(): List<User> {
        return client
            .get("${NetworkConfig.BASE_URL}/users")
            .body()
    }


}