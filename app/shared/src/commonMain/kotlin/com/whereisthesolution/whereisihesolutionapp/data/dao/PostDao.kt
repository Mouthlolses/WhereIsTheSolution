package com.whereisthesolution.whereisihesolutionapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.whereisthesolution.whereisihesolutionapp.data.entity.PostEntity
import com.whereisthesolution.whereisihesolutionapp.data.entity.PostWithUser
import kotlinx.coroutines.flow.Flow


@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: PostEntity)

    // Traz o Post + o User que criou em uma única consulta otimizada
    @Transaction
    @Query("SELECT * FROM posts ORDER BY createdAtTimestamp DESC")
    fun getFeedWithAuthor(): Flow<List<PostWithUser>>

    // Traz todos os posts de um usuário específico junto com os dados dele
    @Transaction
    @Query("SELECT * FROM posts WHERE userId = :userId ORDER BY createdAtTimestamp DESC")
    fun getPostsByUserId(userId: String): Flow<List<PostWithUser>>
}