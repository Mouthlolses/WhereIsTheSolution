package com.whereisthesolution.whereisihesolutionapp.repository

import com.whereisthesolution.whereisihesolutionapp.database.tables.PostImagesTable
import com.whereisthesolution.whereisihesolutionapp.database.tables.PostsTable
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.Post
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.PostResponse
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.PrivacyLevel
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.ReportCategory
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.ReportStatus
import com.whereisthesolution.whereisihesolutionapp.domain.repository.PostRepository
import com.whereisthesolution.whereisihesolutionapp.utils.toPostResponse
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import kotlin.time.Instant

class PostRepositoryImpl : PostRepository {

    override suspend fun createPost(
        post: Post,
        imageUrls: List<String>
    ): Post {

        require(imageUrls.isNotEmpty()) {
            "A post must have at least one image"
        }

        return transaction {
            val generatedPostId = PostsTable.insert {
                it[userId] = post.userId
                it[title] = post.title
                it[description] = post.description
                it[category] = post.category.name
                it[latitude] = post.latitude
                it[longitude] = post.longitude
                it[address] = post.address
                it[status] = post.status.name
                it[privacyLevel] = post.privacyLevel.name
                it[createdAt] = post.createdAt.toEpochMilliseconds()
            } get PostsTable.id

            imageUrls.forEachIndexed { index, imageUrl ->

                PostImagesTable.insert {
                    it[PostImagesTable.postId] = generatedPostId
                    it[PostImagesTable.imageUrl] = imageUrl
                    it[PostImagesTable.position] = index
                }
            }
            post.copy(
                id = generatedPostId
            )
        }
    }

    override suspend fun getFeed(
        page: Int,
        pageSize: Int
    ): List<PostResponse> {

        val offset = page * pageSize

        return transaction {

            val posts = PostsTable
                .selectAll()
                .orderBy(
                    PostsTable.createdAt to SortOrder.DESC
                )
                .limit(pageSize)
                .offset(offset.toLong())
                .map { row ->
                    row
                }

            if (posts.isEmpty()) {
                return@transaction emptyList()
            }

            val postIds = posts.map {
                it[PostsTable.id]
            }

            val images = PostImagesTable
                .selectAll()
                .where {
                    PostImagesTable.postId inList postIds
                }
                .orderBy(
                    PostImagesTable.position to SortOrder.ASC
                )
                .groupBy {
                    it[PostImagesTable.postId]
                }

            posts.map { row ->

                val postId = row[PostsTable.id]

                row.toPostResponse(
                    imageUrls = images[postId]
                        ?.map {
                            it[PostImagesTable.imageUrl]
                        }
                        ?: emptyList()
                )
            }
        }
    }

    override suspend fun getPostsByUser(
        userId: Long,
        page: Int,
        pageSize: Int
    ): List<PostResponse> {

        val offset = page * pageSize

        return transaction {

            val posts = PostsTable
                .selectAll()
                .where {
                    PostsTable.userId eq userId
                }
                .orderBy(
                    PostsTable.createdAt to SortOrder.DESC
                )
                .limit(pageSize)
                .offset(offset.toLong())
                .map { row ->
                    row
                }

            if (posts.isEmpty()) {
                return@transaction emptyList()
            }

            val postIds = posts.map {
                it[PostsTable.id]
            }

            val images = PostImagesTable
                .selectAll()
                .where {
                    PostImagesTable.postId inList postIds
                }
                .orderBy(
                    PostImagesTable.position to SortOrder.ASC
                )
                .groupBy {
                    it[PostImagesTable.postId]
                }

            posts.map { row ->

                val postId = row[PostsTable.id]

                PostResponse(
                    id = postId,
                    userId = row[PostsTable.userId],
                    title = row[PostsTable.title],
                    description = row[PostsTable.description],
                    category = ReportCategory.valueOf(
                        row[PostsTable.category]
                    ),
                    latitude = row[PostsTable.latitude],
                    longitude = row[PostsTable.longitude],
                    address = row[PostsTable.address],
                    status = ReportStatus.valueOf(
                        row[PostsTable.status]
                    ),
                    privacyLevel = PrivacyLevel.valueOf(
                        row[PostsTable.privacyLevel]
                    ),
                    imageUrls = images[postId]
                        ?.map {
                            it[PostImagesTable.imageUrl]
                        }
                        ?: emptyList(),
                    createdAt = Instant.fromEpochMilliseconds(
                        row[PostsTable.createdAt]
                    )
                )
            }
        }
    }

    override suspend fun getPostById(
        postId: Long
    ): PostResponse? {

        return transaction {

            val post = PostsTable
                .selectAll()
                .where {
                    PostsTable.id eq postId
                }
                .singleOrNull()

            if (post == null) {
                return@transaction null
            }

            val images = PostImagesTable
                .selectAll()
                .where {
                    PostImagesTable.postId eq postId
                }
                .orderBy(
                    PostImagesTable.position to SortOrder.ASC
                )
                .map {
                    it[PostImagesTable.imageUrl]
                }

            PostResponse(
                id = post[PostsTable.id],
                userId = post[PostsTable.userId],
                title = post[PostsTable.title],
                description = post[PostsTable.description],
                category = ReportCategory.valueOf(
                    post[PostsTable.category]
                ),
                latitude = post[PostsTable.latitude],
                longitude = post[PostsTable.longitude],
                address = post[PostsTable.address],
                status = ReportStatus.valueOf(
                    post[PostsTable.status]
                ),
                privacyLevel = PrivacyLevel.valueOf(
                    post[PostsTable.privacyLevel]
                ),
                imageUrls = images,
                createdAt = Instant.fromEpochMilliseconds(
                    post[PostsTable.createdAt]
                )
            )
        }
    }

    override suspend fun deletePost(
        postId: Long,
        userId: Long
    ) {
        transaction {

            PostsTable.deleteWhere {
                (PostsTable.id eq postId) and
                        (PostsTable.userId eq userId)
            }
        }
    }
}
