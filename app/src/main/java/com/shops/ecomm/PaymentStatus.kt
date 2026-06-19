package com.shops.ecomm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object PaymentStatus {
    var isSuccess by mutableStateOf(false)
    var orderId by mutableStateOf("")
}
