package com.shops.ecomm.domain.repository

import com.shops.ecomm.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItems(userId: String): Flow<List<CartItem>>
    suspend fun addToCart(userId: String, cartItem: CartItem)
    suspend fun removeFromCart(userId: String, productId: Int)
    suspend fun updateQuantity(userId: String, productId: Int, quantity: Int)
    suspend fun clearCart(userId: String)
}
