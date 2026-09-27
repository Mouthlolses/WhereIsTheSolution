package com.whereisthesolution.whereisihesolutionapp.routes

import com.whereisthesolution.whereisihesolutionapp.domain.model.post.CreatePostRequest
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.Post
import com.whereisthesolution.whereisihesolutionapp.domain.model.user.login.LoginResponse
import com.whereisthesolution.whereisihesolutionapp.dto.CreateUserRequest
import com.whereisthesolution.whereisihesolutionapp.dto.LoginRequest
import com.whereisthesolution.whereisihesolutionapp.security.JwtService
import com.whereisthesolution.whereisihesolutionapp.services.PostService
import com.whereisthesolution.whereisihesolutionapp.services.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.userRoutes(
    userService: UserService,
    postService: PostService,
    jwtService: JwtService
) {

    route("/users") {

        get {
            val users = userService.listUsers()
            call.respond(users)
        }

        // criar usuário
        post("/register") {
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

            val user = userService.login(
                email = request.email,
                password = request.password
            )

            if (user == null) {
                return@post call.respond(HttpStatusCode.Unauthorized)
            }

            val token = jwtService.generateToken(user.id)


            call.respond(
                HttpStatusCode.OK,
                LoginResponse(
                    token = token,
                    user = user
                )
            )
        }

        // buscar usuário
        get("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest)

            val user = userService.findById(id)
                ?: return@get call.respond(HttpStatusCode.NotFound)

            call.respond(user)
        }
    }

    authenticate("jwt-auth") {

        route("/posts") {

            post("/create") {

                val principal = call.principal<JWTPrincipal>()

                val userId = principal
                    ?.payload
                    ?.subject
                    ?.toLongOrNull()
                    ?: return@post call.respond(
                        HttpStatusCode.Unauthorized
                    )

                val request = call.receive<CreatePostRequest>()

                val savedPost = postService.save(
                    post = Post(
                        id = 0,
                        userId = userId,
                        title = request.title,
                        description = request.description,
                        category = request.category,
                        latitude = request.latitude,
                        longitude = request.longitude,
                        address = request.address,
                        privacyLevel = request.privacyLevel
                    ),
                    imageUrls = request.imageUrls
                )


                call.respond(
                    HttpStatusCode.Created,
                    savedPost
                )
            }

        }
    }
}