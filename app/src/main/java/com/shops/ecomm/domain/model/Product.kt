package com.shops.ecomm.domain.model

data class Product(
    val id: Int = 0,
    val title: String = "",
    val price: Int = 0,
    val oldPrice: Int? = null,
    val discount: String? = null,
    val images: List<String> = emptyList(),
    val description: String = "",
    val isWishlisted: Boolean = false
)
