package com.shops.ecomm.domain.model

data class CartItem(
    val product: Product = Product(),
    val quantity: Int = 0
)
