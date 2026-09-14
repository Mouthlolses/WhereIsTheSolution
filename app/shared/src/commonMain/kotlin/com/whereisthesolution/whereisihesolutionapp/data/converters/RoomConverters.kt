package com.whereisthesolution.whereisihesolutionapp.data.converters

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class RoomConverters {
    @TypeConverter
    fun fromStringList(value: List<String>): String = Json.encodeToString(value)

    @TypeConverter
    fun toStringList(value: String): List<String> = try {
        Json.decodeFromString(value)
    } catch (e: Exception) {
        emptyList()
    }
}