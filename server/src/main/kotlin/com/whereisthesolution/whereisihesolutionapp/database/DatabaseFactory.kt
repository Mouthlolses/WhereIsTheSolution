package com.whereisthesolution.whereisihesolutionapp.database

import com.whereisthesolution.whereisihesolutionapp.database.tables.UsersTable
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object DatabaseFactory {

    fun init() {
        var jdbcUrl = System.getenv("DATABASE_URL") ?: "jdbc:postgresql://localhost:5433/whereisthesolution"

        // Força a URL a ter o prefixo jdbc: caso tenha vindo apenas como postgresql://
        if (jdbcUrl.startsWith("postgresql://")) {
            jdbcUrl = "jdbc:$jdbcUrl"
        }

        Database.connect(
            url = jdbcUrl,
            driver = "org.postgresql.Driver"
        )

        transaction {
            SchemaUtils.create(UsersTable)
        }
    }
}