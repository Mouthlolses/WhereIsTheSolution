package com.whereisthesolution.whereisihesolutionapp.storage

import com.whereisthesolution.whereisihesolutionapp.storage.config.SupabaseConfig
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.headers
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess


interface ImageStorage {
    suspend fun upload(
        path: String,
        bytes: ByteArray,
        contentType: String
    ): String

    suspend fun delete(path: String)
}

class SupabaseImageStorage(
    private val client: HttpClient,
    private val config: SupabaseConfig
) : ImageStorage {

    override suspend fun upload(
        path: String,
        bytes: ByteArray,
        contentType: String
    ): String {
        val encodedPath = path
            .split("/")
            .joinToString("/") {
                java.net.URLEncoder.encode(it, "UTF-8")
                    .replace("+", "%20")
            }

        val url =
            "${config.url.trimEnd('/')}/storage/v1/object/" +
                    "${config.bucket}/$encodedPath"

        val response = client.put(url) {
            headers {
                append(
                    HttpHeaders.Authorization,
                    "Bearer ${config.secretKey}"
                )
                append("apikey", config.secretKey)
                append("x-upsert", "false")
            }

            contentType(
                ContentType.parse(contentType)
            )

            setBody(bytes)
        }

        if (!response.status.isSuccess()) {
            error(
                "Falha no upload para o Supabase: " +
                        "${response.status} - ${response.bodyAsText()}"
            )
        }

        return "${config.url.trimEnd('/')}/storage/v1/object/public/" +
                "${config.bucket}/$encodedPath"
    }

    override suspend fun delete(path: String) {
        val encodedPath = path
            .split("/")
            .joinToString("/") {
                java.net.URLEncoder.encode(it, "UTF-8")
                    .replace("+", "%20")
            }

        val url =
            "${config.url.trimEnd('/')}/storage/v1/object/" +
                    "${config.bucket}/$encodedPath"

        val response = client.delete(url) {
            headers {
                append(
                    HttpHeaders.Authorization,
                    "Bearer ${config.secretKey}"
                )
                append("apikey", config.secretKey)
            }
        }

        if (!response.status.isSuccess()) {
            error(
                "Falha ao excluir imagem do Supabase: " +
                        "${response.status} - ${response.bodyAsText()}"
            )
        }
    }
}


