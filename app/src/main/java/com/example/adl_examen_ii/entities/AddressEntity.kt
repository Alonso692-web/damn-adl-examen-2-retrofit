package com.example.adl_examen_ii.entities

data class AddressEntity(
    val id: Long,
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: GeoEntity
)