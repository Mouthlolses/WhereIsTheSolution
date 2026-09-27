package com.whereisthesolution.whereisihesolutionapp.database.tables

import com.whereisthesolution.whereisihesolutionapp.domain.model.post.PrivacyLevel
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.ReportStatus
import org.jetbrains.exposed.v1.core.Table

object PostsTable : Table("posts") {

    val id = long("id").autoIncrement()

    val userId = long("user_id")
        .references(UsersTable.id)

    val title = varchar("title", 150)

    val description = text("description")

    val category = varchar("category", 50)

    val latitude = double("latitude")

    val longitude = double("longitude")

    val address = varchar("address", 255)

    val status = varchar("status", 30)
        .default(ReportStatus.PENDING.name)

    val privacyLevel = varchar("privacy_level", 40)
        .default(PrivacyLevel.PUBLIC_TO_COMMUNITY.name)

    val createdAt = long("created_at")

    override val primaryKey = PrimaryKey(id)
}