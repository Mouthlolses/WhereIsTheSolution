package com.whereisthesolution.whereisihesolutionapp.routes

import com.whereisthesolution.whereisihesolutionapp.domain.model.user.User
import com.whereisthesolution.whereisihesolutionapp.dto.CreateUserRequest
import com.whereisthesolution.whereisihesolutionapp.dto.LoginRequest
import com.whereisthesolution.whereisihesolutionapp.services.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.userRoutes(
    userService: UserService
) {
    route("/users") {

        get {
            val users = userService.listUsers()
            call.respond(users)
        }

        // buscar usuário
        get("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest)

            val user = userService.findById(id)
                ?: return@get call.respond(HttpStatusCode.NotFound)

            call.respond(user)
        }

        // criar usuário
        post {
            val request = call.receive<CreateUserRequest>()

            val savedUser = userService.save(
                name = request.name,
                email = request.email,
                password = request.password
            )

            call.respond(
                HttpStatusCode.Created,
                savedUser
            )
        }

        // fazer login
        post("/auth/login") {

            val request = call.receive<LoginRequest>()

            val isValid = userService.login(
                email = request.email,
                password = request.password
            )

            if (!isValid) {
                return@post call.respond(HttpStatusCode.Unauthorized)
            }

            call.respond(HttpStatusCode.OK)
        }


    }
}