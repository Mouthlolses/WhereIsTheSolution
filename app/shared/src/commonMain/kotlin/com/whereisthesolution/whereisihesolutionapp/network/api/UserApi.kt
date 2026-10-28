package com.whereisthesolution.whereisihesolutionapp.network.api

import com.whereisthesolution.whereisihesolutionapp.domain.model.post.PostResponse
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.FeedPostResponse
import com.whereisthesolution.whereisihesolutionapp.domain.model.user.login.LoginResponse
import com.whereisthesolution.whereisihesolutionapp.network.config.NetworkConfig
import com.whereisthesolution.whereisihesolutionapp.network.dto.AuthUserRequest
import com.whereisthesolution.whereisihesolutionapp.network.dto.CreatePostRequest
import com.whereisthesolution.whereisihesolutionapp.network.dto.RegisterUserRequest
import com.whereisthesolution.whereisihesolutionapp.network.dto.UserResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.statement.bodyAsText
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class UserApi(
    private val client: HttpClient
) {
    suspend fun registerUser(
        request: RegisterUserRequest
    ): UserResponse {

        val response = client
            .post("${NetworkConfig.BASE_URL}/users/register") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }

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

    suspend fun createPost(
        createPostRequest: CreatePostRequest
    ): PostResponse {
        val response = client
            .post("${NetworkConfig.BASE_URL}/posts/create") {
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append("title", createPostRequest.title)
                            append("description", createPostRequest.description)

                            createPostRequest.images?.forEach { image ->
                                append(
                                    "images",
                                    image.bytes,
                                    Headers.build {
                                        append(
                                            HttpHeaders.ContentType,
                                            image.contentType
                                        )
                                        append(
                                            HttpHeaders.ContentDisposition,
                                            "filename=\"${image.extension}\""
                                        )
                                    }
                                )
                            }
                        }
                    )
                )
            }

        if (!response.status.isSuccess()) {
            error("Falha ao criar ocorrência (${response.status}): ${response.bodyAsText()}")
        }

        return response.body()
    }

    suspend fun getAllPosts(
        page: Int,
        pageSize: Int
    ): List<FeedPostResponse> {
        return client
            .get("${NetworkConfig.BASE_URL}/posts/get/allposts") {
                parameter("page", page)
                parameter("pageSize", pageSize)
                contentType(ContentType.Application.Json)
            }
            .body()
    }
}
