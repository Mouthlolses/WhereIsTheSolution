package com.whereisthesolution.whereisihesolutionapp.services

import com.whereisthesolution.whereisihesolutionapp.domain.model.post.FeedPostResponse
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.Post
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.PostResponse
import com.whereisthesolution.whereisihesolutionapp.domain.repository.PostRepository
import com.whereisthesolution.whereisihesolutionapp.dto.ImageUpload
import com.whereisthesolution.whereisihesolutionapp.storage.ImageStorage
import com.whereisthesolution.whereisihesolutionapp.utils.toResponse
import java.util.UUID

class PostService(
    private val postRepository: PostRepository,
    private val imageStorage: ImageStorage
) {
    suspend fun save(
        post: Post,
        images: List<ImageUpload>
    ): PostResponse {

        val imageUrl = images.map { image ->
            val path = "posts/${UUID.randomUUID()}.${image.extension}"

            imageStorage.upload(
                path = path,
                bytes = image.bytes,
                contentType = image.contentType
            )
        }

        val savedPost = postRepository.createPost(
            post = post,
            imageUrls = imageUrl
        )

        return savedPost.toResponse(imageUrl)
    }

    suspend fun getPosts(
        page: Int,
        pageSize: Int
    ): List<FeedPostResponse> {
        return postRepository.getFeed(
            page = page,
            pageSize = pageSize
        )
    }

}