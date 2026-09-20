package com.whereisthesolution.whereisihesolutionapp.security

import at.favre.lib.crypto.bcrypt.BCrypt

class PasswordHasher {


    //for save user password
    fun hash(password: String): String {
        return BCrypt.withDefaults()
            .hashToString(12, password.toCharArray())
    }


    //for login
    fun verify(
        password: String,
        hash: String
    ): Boolean {
        return BCrypt.verifyer()
            .verify(password.toCharArray(), hash)
            .verified
    }
}