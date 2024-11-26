package com.example.adl_09.repositories

import com.example.adl_09.services.UserService
import com.example.adl_examen_ii.entities.UserEntity
import com.example.adl_examen_ii.network.ClienteRetrofit

class UserRepository(private val userService: UserService = ClienteRetrofit.getInstanciaRetrofit) {

    suspend fun getAllUsers(): List<UserEntity> = userService.getAllUsers()
}