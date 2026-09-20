package com.whereisthesolution.whereisihesolutionapp.database

import com.whereisthesolution.whereisihesolutionapp.database.tables.UsersTable
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object DatabaseFactory {

    fun init() {
        val host = System.getenv("DATABASE_HOST") ?: "aws-0-us-east-1.pooler.supabase.com"
        val port = System.getenv("DATABASE_PORT") ?: "5432"
        val dbName = System.getenv("DATABASE_NAME") ?: "postgres"
        val user = System.getenv("DATABASE_USER") ?: "postgres.aaoebhheqzpdzavomyey"
        val password = System.getenv("DATABASE_PASSWORD") ?: "mikeytoman1321"

        val jdbcUrl = "jdbc:postgresql://$host:$port/$dbName"

        Database.connect(
            url = jdbcUrl,
            driver = "org.postgresql.Driver",
            user = user,
            password = password
        )

        transaction {
            SchemaUtils.create(UsersTable)
        }
    }
}