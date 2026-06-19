package com.shops.ecomm.presentation.cart

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.razorpay.Checkout
import org.json.JSONObject
import android.app.Activity
import com.shops.ecomm.presentation.search.SearchViewModel
import com.shops.ecomm.presentation.order.OrderViewModel
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.launch
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.paymentsheet.rememberPaymentSheet
import com.shops.ecomm.PaymentStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    totalAmount: Int,
    onBack: () -> Unit,
    onOrderPlaced: (String) -> Unit,
    viewModel: OrderViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    searchViewModel: SearchViewModel = hiltViewModel(),
    userViewModel: com.shops.ecomm.presentation.profile.UserViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    var selectedPayment by remember { mutableStateOf("Razorpay") }
    val context = LocalContext.current
    val cartItems by cartViewModel.cartItems.collectAsState()
    val orderIdState by viewModel.orderPlaced.collectAsState()
    val userProfile by userViewModel.userProfile.collectAsState()
    var isProcessing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val userName = userProfile?.name ?: "User"
    val userAddress = userProfile?.address ?: "123 Shopping Street, Tech City"
    val userPhone = userProfile?.phoneNumber ?: "+91 98765 43210"

    val razorpay = remember { Checkout() }

    val paymentSheet = rememberPaymentSheet { result ->
        isProcessing = false
        when (result) {
            is PaymentSheetResult.Completed -> {
                viewModel.placeOrder(cartItems, totalAmount, "Stripe", userAddress)
                Toast.makeText(context, "Payment Successful!", Toast.LENGTH_LONG).show()
            }
            is PaymentSheetResult.Canceled -> {
                Toast.makeText(context, "Payment Canceled", Toast.LENGTH_SHORT).show()
            }
            is PaymentSheetResult.Failed -> {
                Toast.makeText(context, "Payment Failed: ${result.error.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }

    LaunchedEffect(orderIdState) {
        orderIdState?.let { id ->
            kotlinx.coroutines.delay(1500) // 1.5 second delay
            onOrderPlaced(id)
            viewModel.resetState()
        }
    }

    LaunchedEffect(PaymentStatus.isSuccess) {
        if (PaymentStatus.isSuccess) {
            viewModel.placeOrder(cartItems, totalAmount, "Razorpay", userAddress)
            PaymentStatus.isSuccess = false
        }
    }

    fun startStripePayment() {
        isProcessing = true
        scope.launch {
            try {
                val clientSecret = searchViewModel.repository.createPaymentIntent((totalAmount * 1.05 * 100).toInt())
                if (clientSecret != null) {
                    paymentSheet.presentWithPaymentIntent(clientSecret)
                } else {
                    isProcessing = false
                    Toast.makeText(context, "Failed to initialize payment gateway", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                isProcessing = false
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun startRazorpayPayment() {
        val activity = context as? Activity ?: return
        razorpay.setKeyID("rzp_test_tH7nTQabmzh9IE")
        
        try {
            val options = JSONObject()
            options.put("name", "LUXURA")
            options.put("description", "Purchase Payment")
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("currency", "INR")
            options.put("amount", (totalAmount * 105).toString())
            
            val prefill = JSONObject()
            prefill.put("email", userProfile?.email ?: "test@luxura.com")
            prefill.put("contact", userPhone)
            options.put("prefill", prefill)

            razorpay.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(context, "Razorpay Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    if (isProcessing) {
        AlertDialog(
            onDismissRequest = { },
            confirmButton = { },
            title = { Text("Opening Secure Gateway") },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    Text("Please wait...", fontSize = 14.sp, color = Color.Gray)
                }
            },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Checkout", 
                        fontWeight = FontWeight.Black
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 0.dp,
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(0.dp)
            ) {
                Column(modifier = Modifier.navigationBarsPadding().padding(24.dp)) {
                    HorizontalDivider(modifier = Modifier.padding(bottom = 16.dp), color = Color.LightGray.copy(alpha = 0.3f))
                    Button(
                        onClick = {
                            when (selectedPayment) {
                                "Stripe" -> startStripePayment()
                                "Razorpay" -> startRazorpayPayment()
                                else -> viewModel.placeOrder(cartItems, totalAmount, "COD", userAddress)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = null
                    ) {
                        Text("Place Order • ₹${(totalAmount * 1.05).toInt()}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(scrollState)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            CheckoutSection(title = "Shipping Address", icon = Icons.Default.LocationOn) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(userName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                            Text(userAddress, color = Color.Gray, fontSize = 14.sp)
                            Text(userPhone, color = Color.Gray, fontSize = 14.sp)
                        }
                        TextButton(onClick = { /* User can change in profile */ }) {
                            Text("Change", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold) 
                        }
                    }
                }
            }

            CheckoutSection(title = "Payment Method", icon = Icons.Default.Payment) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    PaymentOption("Razorpay (UPI, PhonePe)", Icons.Default.Payment, selectedPayment == "Razorpay") { selectedPayment = "Razorpay" }
                    PaymentOption("Cash on Delivery (COD)", Icons.Default.Money, selectedPayment == "COD") { selectedPayment = "COD" }
                }
            }

            CheckoutSection(title = "Price Details", icon = Icons.AutoMirrored.Filled.ReceiptLong) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        SummaryRow("Subtotal", "₹$totalAmount")
                        SummaryRow("Shipping Fee", "FREE", isHighlight = true)
                        SummaryRow("GST (5%)", "₹${(totalAmount * 0.05).toInt()}")
                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = Color.LightGray.copy(alpha = 0.3f))
                        SummaryRow("Total Payable", "₹${(totalAmount * 1.05).toInt()}", isTotal = true)
                    }
                }
            }
            
            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
fun CheckoutSection(title: String, icon: ImageVector, content: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                modifier = Modifier.size(32.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                }
            }
            Spacer(Modifier.width(12.dp))
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black)
        }
        content()
    }
}

@Composable
fun PaymentOption(title: String, icon: ImageVector, isSelected: Boolean, onSelect: () -> Unit) {
    Surface(
        onClick = onSelect,
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.05f) else MaterialTheme.colorScheme.surface,
        border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.2f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray)
            Spacer(Modifier.width(16.dp))
            Text(title, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium)
            Spacer(Modifier.weight(1f))
            RadioButton(selected = isSelected, onClick = onSelect)
        }
    }
}

@Composable
fun SummaryRow(label: String, value: String, isTotal: Boolean = false, isHighlight: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = if (isTotal) Color.Black else Color.Gray, fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Medium)
        Text(
            text = value,
            fontWeight = if (isTotal) FontWeight.Black else FontWeight.Bold,
            fontSize = if (isTotal) 20.sp else 14.sp,
            color = if (isHighlight) Color(0xFF4CAF50) else if (isTotal) MaterialTheme.colorScheme.primary else Color.Black
        )
    }
}
