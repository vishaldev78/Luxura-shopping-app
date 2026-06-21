package com.shops.ecomm.data.remote

import com.google.gson.annotations.SerializedName

data class CategoryDTO(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("slug")
    val slug: String?,

    @SerializedName("image")
    val image: String?,

    @SerializedName("creationAt")
    val creationAt: String?,

    @SerializedName("updatedAt")
    val updatedAt: String?
)
