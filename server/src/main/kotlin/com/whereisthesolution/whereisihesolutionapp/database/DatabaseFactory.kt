package com.whereisthesolution.whereisihesolutionapp.database

import com.whereisthesolution.whereisihesolutionapp.database.tables.UsersTable
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object DatabaseFactory {

    fun init() {
        // Lemos as credenciais das variáveis do Render ou usamos fallbacks locais
        val envUrl = System.getenv("DATABASE_URL") ?: "jdbc:postgresql://aws-0-us-east-1.pooler.supabase.com:5432/postgres"
        val user = System.getenv("DATABASE_USER") ?: "postgres.aaoebhheqzpdzavomyey"
        val password = System.getenv("DATABASE_PASSWORD") ?: "mikeytoman1321"

        // Garante que a URL comece com jdbc:postgresql:// e limpa caso tenha usuário/senha embutidos
        var jdbcUrl = envUrl
        if (jdbcUrl.contains("@")) {
            jdbcUrl = "jdbc:postgresql://" + jdbcUrl.substringAfter("@")
        } else if (!jdbcUrl.startsWith("jdbc:")) {
            jdbcUrl = "jdbc:$jdbcUrl"
        }

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