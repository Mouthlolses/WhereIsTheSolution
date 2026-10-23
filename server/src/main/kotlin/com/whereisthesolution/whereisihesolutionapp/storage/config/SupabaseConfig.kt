package com.whereisthesolution.whereisihesolutionapp.storage.config

data class SupabaseConfig(
    val url: String,
    val secretKey: String,
    val bucket: String
) {
    companion object {
        fun fromEnvironment() = SupabaseConfig(
            url = requireNotNull(
                System.getenv("SUPABASE_URL")
            ),
            secretKey = requireNotNull(
                System.getenv("SUPABASE_SECRET_KEY")
            ),
            bucket = System.getenv("SUPABASE_STORAGE_BUCKET")
                ?: "post-images"
        )
    }
}