package com.whereisthesolution.whereisihesolutionapp.routes

import com.whereisthesolution.whereisihesolutionapp.domain.model.post.Post
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.PrivacyLevel
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.ReportCategory
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.image.CreatePostRequest
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.image.ImageUpload
import com.whereisthesolution.whereisihesolutionapp.domain.model.user.login.LoginResponse
import com.whereisthesolution.whereisihesolutionapp.dto.CreateUserRequest
import com.whereisthesolution.whereisihesolutionapp.dto.LoginRequest
import com.whereisthesolution.whereisihesolutionapp.security.JwtService
import com.whereisthesolution.whereisihesolutionapp.services.PostService
import com.whereisthesolution.whereisihesolutionapp.services.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.server.application.log
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.request.receiveMultipart
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.utils.io.core.readBytes
import io.ktor.utils.io.readBuffer

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

                val multipart = call.receiveMultipart()

                var title = ""
                var description = ""
                var category: ReportCategory = ReportCategory.OUTROS
                var latitude: Double? = null
                var longitude: Double? = null
                var address = ""
                var privacyLevel: PrivacyLevel = PrivacyLevel.PUBLIC_TO_COMMUNITY

                val images = mutableListOf<ImageUpload>()

                multipart.forEachPart { part ->
                    when (part) {
                        is PartData.FormItem -> {
                            when (part.name) {
                                "title" -> title = part.value
                                "description" -> description = part.value
                                "category" -> {
                                    category = runCatching {
                                        ReportCategory.valueOf(part.value)
                                    }.getOrDefault(ReportCategory.OUTROS)
                                }

                                "latitude" -> latitude =
                                    part.value.toDoubleOrNull()

                                "longitude" -> longitude =
                                    part.value.toDoubleOrNull()

                                "address" -> address = part.value
                                "privacyLevel" -> {
                                    privacyLevel = runCatching {
                                        PrivacyLevel.valueOf(part.value)
                                    }.getOrDefault(PrivacyLevel.PUBLIC_TO_COMMUNITY)
                                }
                            }
                        }

                        is PartData.FileItem -> {
                            if (part.name == "images") {
                                val bytes = part.provider()
                                    .readBuffer()
                                    .readBytes()

                                val contentType = part.contentType
                                    ?.toString()
                                    ?: "application/octet-stream"

                                val extension = when (contentType) {
                                    "image/jpeg" -> "jpg"
                                    "image/png" -> "png"
                                    "image/webp" -> "webp"
                                    else -> ""
                                }

                                if (extension.isNotEmpty()) {
                                    images.add(
                                        ImageUpload(
                                            bytes = bytes,
                                            contentType = contentType,
                                            extension = extension
                                        )
                                    )
                                }
                            }
                        }

                        else -> Unit
                    }

                    part.release()
                }

                if (images.isEmpty()) {
                    return@post call.respond(
                        HttpStatusCode.BadRequest,
                        "A ocorrência precisa ter pelo menos uma imagem válida."
                    )
                }

                if (latitude == null || longitude == null) {
                    return@post call.respond(
                        HttpStatusCode.BadRequest,
                        "Latitude e longitude são obrigatórias."
                    )
                }


                try {
                    val savedPost = postService.save(
                        post = CreatePostRequest(
                            post = Post(
                                id = 0,
                                userId = userId,
                                title = title,
                                description = description,
                                category = category,
                                latitude = latitude!!,
                                longitude = longitude!!,
                                address = address,
                                privacyLevel = privacyLevel
                            ),
                            images = images
                        )
                    )

                    call.respond(
                        HttpStatusCode.Created, savedPost
                    )
                } catch (e: Exception) {
                    call.application.log.error(
                        "Erro ao criar ocorrência",
                        e
                    )
                }
            }


            get("/get/allposts") {

                val page = call.request.queryParameters["page"]
                    ?.toIntOrNull()
                    ?: 1

                val pageSize = call.request.queryParameters["pageSize"]
                    ?.toIntOrNull()
                    ?: 20

                try {
                    val posts = postService.getPosts(
                        page = page,
                        pageSize = pageSize
                    )

                    call.respond(HttpStatusCode.OK, posts)

                } catch (e: IllegalArgumentException) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to (e.message ?: "Parametros inválidos"))
                    )
                }

            }
        }
    }
}