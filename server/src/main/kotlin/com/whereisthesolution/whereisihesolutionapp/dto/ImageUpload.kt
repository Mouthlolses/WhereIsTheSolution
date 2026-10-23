package com.whereisthesolution.whereisihesolutionapp.dto

data class ImageUpload(
    val bytes: ByteArray,
    val contentType: String,
    val extension: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImageUpload

        if (!bytes.contentEquals(other.bytes)) return false
        if (contentType != other.contentType) return false
        if (extension != other.extension) return false

        return true
    }

    override fun hashCode(): Int {
        var result = bytes.contentHashCode()
        result = 31 * result + contentType.hashCode()
        result = 31 * result + extension.hashCode()
        return result
    }
}