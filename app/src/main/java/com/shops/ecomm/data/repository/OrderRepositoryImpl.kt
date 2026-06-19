package com.shops.ecomm.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.shops.ecomm.domain.model.Order
import com.shops.ecomm.domain.repository.OrderRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : OrderRepository {

    private val ordersCollection = firestore.collection("orders")

    override fun getOrders(userId: String): Flow<List<Order>> = callbackFlow {
        val subscription = ordersCollection
            .whereEqualTo("userId", userId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val orders = snapshot?.documents?.mapNotNull { it.toObject(Order::class.java) } ?: emptyList()
                trySend(orders)
            }
        awaitClose { subscription.remove() }
    }

    override suspend fun placeOrder(order: Order) {
        ordersCollection.document(order.orderId).set(order).await()
    }

    override fun getOrderStatus(orderId: String): Flow<String> = callbackFlow {
        val subscription = ordersCollection.document(orderId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val status = snapshot?.getString("status") ?: "Pending"
                trySend(status)
            }
        awaitClose { subscription.remove() }
    }
}
