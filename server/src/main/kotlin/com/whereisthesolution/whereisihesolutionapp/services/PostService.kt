package com.whereisthesolution.whereisihesolutionapp.services

import com.whereisthesolution.whereisihesolutionapp.domain.model.post.Post
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.PostResponse
import com.whereisthesolution.whereisihesolutionapp.domain.repository.PostRepository
import com.whereisthesolution.whereisihesolutionapp.utils.toResponse

class PostService(
    private val postRepository: PostRepository,
) {

    suspend fun save(
        post: Post,
        imageUrls: List<String>
    ): PostResponse {

        val savedPost = postRepository.createPost(
            post = post,
            imageUrls = imageUrls
        )

        return savedPost.toResponse(imageUrls)
    }

    suspend fun getFeed(
        page: Int,
        pageSize: Int
    ): List<PostResponse> {
        return postRepository.getFeed(
            page = page,
            pageSize = pageSize
        )
    }

}