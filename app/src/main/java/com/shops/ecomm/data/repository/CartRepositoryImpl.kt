package com.shops.ecomm.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.shops.ecomm.domain.model.CartItem
import com.shops.ecomm.domain.repository.CartRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : CartRepository {

    private fun getCartCollection(userId: String) = 
        firestore.collection("users").document(userId).collection("cart")

    override fun getCartItems(userId: String): Flow<List<CartItem>> = callbackFlow {
        val subscription = getCartCollection(userId).addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            val items = snapshot?.documents?.mapNotNull { it.toObject(CartItem::class.java) } ?: emptyList()
            trySend(items)
        }
        awaitClose { subscription.remove() }
    }

    override suspend fun addToCart(userId: String, cartItem: CartItem) {
        getCartCollection(userId).document(cartItem.product.id.toString()).set(cartItem).await()
    }

    override suspend fun removeFromCart(userId: String, productId: Int) {
        getCartCollection(userId).document(productId.toString()).delete().await()
    }

    override suspend fun updateQuantity(userId: String, productId: Int, quantity: Int) {
        getCartCollection(userId).document(productId.toString()).update("quantity", quantity).await()
    }

    override suspend fun clearCart(userId: String) {
        val snapshot = getCartCollection(userId).get().await()
        val batch = firestore.batch()
        for (doc in snapshot.documents) {
            batch.delete(doc.reference)
        }
        batch.commit().await()
    }
}
