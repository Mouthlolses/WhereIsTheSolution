package com.whereisthesolution.whereisihesolutionapp.plugins

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
fun Application.configureSerialization() {

    val serializersModule = SerializersModule {
        contextual(Instant::class, Instant.serializer())
    }

    install(ContentNegotiation) {
        json(
            Json {
                this.serializersModule = serializersModule
            }
        )
    }
}