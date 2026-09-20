package com.whereisthesolution.whereisihesolutionapp.data.mappers

import com.whereisthesolution.whereisihesolutionapp.data.entity.PostEntity
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.Post
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.ReportCategory
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.ReportStatus
import kotlinx.serialization.json.Json
import kotlin.time.Instant

// Converter de Entity (Room) -> Domain Model (Core)
fun PostEntity.toDomainModel(): Post {
    return Post(
        id = id,
        userId = userId,
        title = title,
        description = description,
        // Converte a String JSON de volta para List<String>
        imageUrls = try {
            Json.decodeFromString<List<String>>(imageUrlsJson)
        } catch (e: Exception) {
            emptyList()
        },
        // Converte as Strings salvas no banco para os Enums de Domínio
        category = try {
            ReportCategory.valueOf(category)
        } catch (e: Exception) {
            ReportCategory.OUTROS
        },
        status = try {
            ReportStatus.valueOf(status)
        } catch (e: Exception) {
            ReportStatus.PENDING
        },
        latitude = latitude,
        longitude = longitude,
        address = address,
        createdAt = Instant.fromEpochMilliseconds(createdAtTimestamp)
    )
}

// Converter de Domain Model (Core) -> Entity (Room)
fun Post.toEntity(): PostEntity {
    return PostEntity(
        id = id,
        userId = userId,
        title = title,
        description = description,
        // Serializa a List<String> para uma String JSON
        imageUrlsJson = Json.encodeToString(imageUrls),
        category = category.name,
        status = status.name,
        latitude = latitude,
        longitude = longitude,
        address = address,
        // Converte o Instant para Epoch Millis (Long)
        createdAtTimestamp = createdAt.toEpochMilliseconds()
    )
}