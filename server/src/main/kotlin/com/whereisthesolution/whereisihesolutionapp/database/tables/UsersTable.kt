package com.whereisthesolution.whereisihesolutionapp.database.tables

import org.jetbrains.exposed.v1.core.Table


//representação da infraestrutura do banco.

object UsersTable : Table("users") {

    val id = long("id").autoIncrement()
    val name = varchar("name", 100)
    val email = varchar("email", 150)
    val passwordHash = varchar("passwordHash", 60)

    override val primaryKey = PrimaryKey(id)
}