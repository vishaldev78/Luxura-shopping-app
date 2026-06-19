package com.shops.ecomm.data.remote


data class ProductDTO(

    val id: Int,

    val title: String,

    val slug: String,

    val price: Int,

    val description: String,

    val category: CategoryDTO,

    val images: List<String>

)
