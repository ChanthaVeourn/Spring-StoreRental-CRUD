package com.example.storeRental.dto

data class UserDto (
    val username: String?,
    val email:String?,
    val hashPass:String? = null
)