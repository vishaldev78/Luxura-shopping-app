package com.shops.ecomm.data.remote

import com.google.gson.annotations.SerializedName

data class ProductDTO(

    @SerializedName("id")
    val id: Int?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("slug")
    val slug: String?,

    @SerializedName("price")
    val price: Int?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("category")
    val category: CategoryDTO?,

    @SerializedName("images")
    val images: List<String>?

)