package com.shops.ecomm.presentation.wishlist

import androidx.lifecycle.ViewModel
import com.shops.ecomm.domain.model.Product
import com.shops.ecomm.util.LocalWishlistManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val wishlistManager: LocalWishlistManager
) : ViewModel() {

    val wishlistItems: StateFlow<List<Product>> = wishlistManager.wishlistItems

    fun toggleWishlist(product: Product) {
        wishlistManager.toggleWishlist(product)
    }
    
    fun isInWishlist(productId: Int): Boolean {
        return wishlistItems.value.any { it.id == productId }
    }
}
