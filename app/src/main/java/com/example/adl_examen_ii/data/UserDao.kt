package com.example.adl_examen_ii.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.adl_examen_ii.entities.User
import com.example.adl_examen_ii.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * from user")
    fun getAll(): Flow<List<User>>

    @Query("SELECT * from user WHERE id = :id")
    fun getById(id: Long): Flow<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(user: User): Long

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)
}