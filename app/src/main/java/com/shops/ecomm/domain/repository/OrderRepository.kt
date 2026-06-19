package com.shops.ecomm.domain.repository

import com.shops.ecomm.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getOrders(userId: String): Flow<List<Order>>
    suspend fun placeOrder(order: Order)
    fun getOrderStatus(orderId: String): Flow<String>
}
