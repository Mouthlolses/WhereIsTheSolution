package com.whereisthesolution.whereisihesolutionapp.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.Date

class JwtService {

    private val secret = "uma-chave-secreta-aqui"

    private val algorithm = Algorithm.HMAC256(secret)

    fun generateToken(userId: Long): String {
        return JWT.create()
            .withSubject(userId.toString())
            .withExpiresAt(
                Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24)
            )
            .sign(algorithm)
    }
}