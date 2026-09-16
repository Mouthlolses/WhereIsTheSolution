package com.whereisthesolution.whereisihesolutionapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.whereisthesolution.whereisihesolutionapp.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    // 1. Criar / Cadastrar um novo usuário
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    // 2. Atualizar dados do perfil (Ex: alterou foto, bairro ou reputação)
    @Update
    suspend fun updateUser(user: UserEntity)

    // 3. Buscar um usuário específico pelo ID (uma única vez)
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): UserEntity?

    // 4. Observar em tempo real os dados do usuário logado (Reativo com Flow)
    @Query("SELECT * FROM users WHERE id = :userId")
    fun observeUserById(userId: Long): Flow<UserEntity?>

    // 5. Deletar a conta do usuário (Como o PostEntity tem CASCADE, apaga os posts dele também)
    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUserById(userId: String)


}