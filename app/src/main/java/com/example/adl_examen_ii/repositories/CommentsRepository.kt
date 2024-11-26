package com.example.adl_examen_ii.repositories


import com.example.adl_examen_ii.network.ClienteRetrofit
import com.example.adl_examen_ii.services.CommentsService

class CommentsRepository(private val commentsService: CommentsService = ClienteRetrofit.getInstanciaRetrofitComments) {
    suspend fun getAllComments(id: Long) = commentsService.getAllComments(id)

}