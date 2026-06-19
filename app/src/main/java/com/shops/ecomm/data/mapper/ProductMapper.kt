package com.shops.ecomm.data.mapper

import com.shops.ecomm.data.remote.ProductDTO
import com.shops.ecomm.domain.model.Product


fun ProductDTO.toProduct(): Product {
    return Product(
        id = id,
        title = title,
        price = price,
        oldPrice = (price * 1.2).toInt(), // Mocking comparison price
        discount = "20% OFF", // Mocking discount
        images = images,
        description = description,
        isWishlisted = false
    )
}