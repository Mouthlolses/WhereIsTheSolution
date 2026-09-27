package com.whereisthesolution.whereisihesolutionapp.database.tables

import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.Table

object PostImagesTable : Table("post_images") {

    val id = long("id").autoIncrement()

    val postId = long("post_id")
        .references(PostsTable.id, onDelete = ReferenceOption.CASCADE)

    val imageUrl = text("image_url")

    val position = integer("position")

    override val primaryKey = PrimaryKey(id)
}