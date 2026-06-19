package com.shops.ecomm.domain.model

data class Order(
    val orderId: String = "",
    val userId: String = "",
    val items: List<CartItem> = emptyList(),
    val totalAmount: Int = 0,
    val status: String = "Pending", // Pending, Confirmed, Shipped, Delivered
    val paymentMethod: String = "COD",
    val timestamp: Long = System.currentTimeMillis(),
    val address: String = ""
)
