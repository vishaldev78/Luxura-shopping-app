package com.shops.ecomm.data.mapper

import com.shops.ecomm.data.remote.ProductDTO
import com.shops.ecomm.domain.model.Product


fun ProductDTO.toProduct(): Product {
    return Product(
        id = id ?: 0,
        title = title ?: "No Title",
        price = price ?: 0,
        oldPrice = ((price ?: 0) * 1.2).toInt(),
        discount = "20% OFF",
        images = images ?: emptyList(),
        description = description ?: "",
        isWishlisted = false
    )
}