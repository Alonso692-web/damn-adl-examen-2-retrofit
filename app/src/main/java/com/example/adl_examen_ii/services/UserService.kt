package com.example.adl_09.services

import com.example.adl_examen_ii.entities.UserEntity
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {

    @GET("users")
    suspend fun getAllUsers(): List<UserEntity>

}