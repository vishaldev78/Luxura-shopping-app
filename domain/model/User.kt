package com.shops.ecomm.domain.model

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val profilePicture: String = "",
    val address: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
