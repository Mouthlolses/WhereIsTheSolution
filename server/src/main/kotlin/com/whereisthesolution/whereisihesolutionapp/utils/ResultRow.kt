package com.whereisthesolution.whereisihesolutionapp.utils

import com.whereisthesolution.whereisihesolutionapp.database.tables.PostsTable
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.PostResponse
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.PrivacyLevel
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.ReportCategory
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.ReportStatus
import org.jetbrains.exposed.v1.core.ResultRow
import kotlin.time.Instant

fun ResultRow.toPostResponse(
    imageUrls: List<String>
): PostResponse {

    return PostResponse(
        id = this[PostsTable.id],
        userId = this[PostsTable.userId],
        title = this[PostsTable.title],
        description = this[PostsTable.description],
        category = ReportCategory.valueOf(
            this[PostsTable.category]
        ),
        latitude = this[PostsTable.latitude],
        longitude = this[PostsTable.longitude],
        address = this[PostsTable.address],
        status = ReportStatus.valueOf(
            this[PostsTable.status]
        ),
        privacyLevel = PrivacyLevel.valueOf(
            this[PostsTable.privacyLevel]
        ),
        imageUrls = imageUrls,
        createdAt = Instant.fromEpochMilliseconds(
            this[PostsTable.createdAt]
        )
    )
}