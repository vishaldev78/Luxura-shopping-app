package com.shops.ecomm.util

import com.shops.ecomm.domain.model.CartItem
import com.shops.ecomm.domain.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalCartManager @Inject constructor() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems = _cartItems.asStateFlow()

    fun addToCart(product: Product) {
        _cartItems.update { currentList ->
            val existingItem = currentList.find { it.product.id == product.id }
            if (existingItem != null) {
                currentList.map {
                    if (it.product.id == product.id) it.copy(quantity = it.quantity + 1)
                    else it
                }
            } else {
                currentList + CartItem(product, 1)
            }
        }
    }

    fun removeFromCart(productId: Int) {
        _cartItems.update { currentList ->
            currentList.filter { it.product.id != productId }
        }
    }

    fun updateQuantity(productId: Int, quantity: Int) {
        if (quantity <= 0) {
            removeFromCart(productId)
            return
        }
        _cartItems.update { currentList ->
            currentList.map {
                if (it.product.id == productId) it.copy(quantity = quantity)
                else it
            }
        }
    }
    
    fun clearCart() {
        _cartItems.value = emptyList()
    }
}

@Singleton
class LocalWishlistManager @Inject constructor() {
    private val _wishlistItems = MutableStateFlow<List<Product>>(emptyList())
    val wishlistItems = _wishlistItems.asStateFlow()

    fun toggleWishlist(product: Product) {
        _wishlistItems.update { currentList ->
            if (currentList.any { it.id == product.id }) {
                currentList.filter { it.id != product.id }
            } else {
                currentList + product
            }
        }
    }
}
