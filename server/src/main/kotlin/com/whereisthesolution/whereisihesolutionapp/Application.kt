package com.whereisthesolution.whereisihesolutionapp

import com.whereisthesolution.whereisihesolutionapp.database.DatabaseFactory
import com.whereisthesolution.whereisihesolutionapp.plugins.configureKoin
import com.whereisthesolution.whereisihesolutionapp.plugins.configureSerialization
import com.whereisthesolution.whereisihesolutionapp.routes.userRoutes
import com.whereisthesolution.whereisihesolutionapp.security.configureSecurity
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.ktor.ext.get

//Fluxo: POST /users -> UserRoutes -> UserService ->  UserRepository -> Database

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {

    DatabaseFactory.init()

    configureSerialization()
    configureSecurity()
    configureKoin()


    routing {

        get("/") {
            call.respondText("Ktor")
        }

        authenticate("api-key-auth") {
            userRoutes(
                userService = get()
            )
        }
    }
}