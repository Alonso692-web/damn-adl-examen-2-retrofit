package com.example.adl_examen_ii.services

import com.example.adl_examen_ii.entities.CommentsEntity
import com.example.adl_examen_ii.entities.PostEntity
import com.example.adl_examen_ii.entities.UserEntity
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CommentsService {

    @GET("posts/{id}/comments")
    suspend fun getAllComments(@Path("id") id: Long): List<CommentsEntity>


}