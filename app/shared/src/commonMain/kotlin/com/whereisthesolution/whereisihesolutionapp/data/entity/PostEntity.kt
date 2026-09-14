package com.whereisthesolution.whereisihesolutionapp.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "posts",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE // Apaga os posts se o usuário for deletado
        )
    ],
    indices = [Index(value = ["userId"])] // Índice melhora performance nas buscas por posts do usuário
)
data class PostEntity(
    @PrimaryKey val id: Long,
    val userId: String, // Chave Estrangeira (Foreign Key)
    val title: String,
    val description: String,
    val imageUrlsJson: String, // Ex: convertido para JSON string via TypeConverter
    val category: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val status: String,
    val createdAtTimestamp: Long
)
