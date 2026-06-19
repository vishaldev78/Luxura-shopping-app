package com.shops.ecomm.presentation.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.shops.ecomm.domain.model.CartItem
import com.shops.ecomm.domain.model.Order
import com.shops.ecomm.domain.repository.CartRepository
import com.shops.ecomm.domain.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _orderPlaced = MutableStateFlow<String?>(null)
    val orderPlaced: StateFlow<String?> = _orderPlaced.asStateFlow()

    fun placeOrder(items: List<CartItem>, totalAmount: Int, paymentMethod: String, address: String) {
        val userId = auth.currentUser?.uid ?: return
        val orderId = "ORD" + UUID.randomUUID().toString().substring(0, 8).uppercase()
        
        val order = Order(
            orderId = orderId,
            userId = userId,
            items = items,
            totalAmount = totalAmount,
            paymentMethod = paymentMethod,
            address = address,
            status = "Placed"
        )

        viewModelScope.launch {
            orderRepository.placeOrder(order)
            cartRepository.clearCart(userId)
            _orderPlaced.value = orderId
        }
    }

    fun resetState() {
        _orderPlaced.value = null
    }
}
