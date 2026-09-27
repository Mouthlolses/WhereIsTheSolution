package com.whereisthesolution.whereisihesolutionapp.domain.repository

import com.whereisthesolution.whereisihesolutionapp.domain.model.post.Post
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.PostResponse

interface PostRepository {

    suspend fun createPost(
        post: Post,
        imageUrls: List<String>
    ): Post


    suspend fun getFeed(
        page: Int,
        pageSize: Int
    ): List<PostResponse>

    suspend fun getPostsByUser(
        userId: Long,
        page: Int,
        pageSize: Int
    ): List<PostResponse>

    suspend fun getPostById(
        postId: Long
    ): PostResponse?

    suspend fun deletePost(
        postId: Long,
        userId: Long
    )
}