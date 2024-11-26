package com.example.adl_examen_ii.services

import com.example.adl_examen_ii.entities.PostEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface PostService {

    @GET("users/{id}/posts")
    suspend fun getAllPost(@Path("id") id: Long): List<PostEntity>

}