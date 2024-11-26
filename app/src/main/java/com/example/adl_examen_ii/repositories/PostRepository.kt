package com.example.adl_examen_ii.repositories


import com.example.adl_examen_ii.network.ClienteRetrofit
import com.example.adl_examen_ii.services.PostService

class PostRepository(private val postService: PostService = ClienteRetrofit.getInstanciaRetrofitPost) {
    suspend fun getAllPost(id: Long) = postService.getAllPost(id)

}