package com.shops.ecomm.navigation

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shops.ecomm.presentation.cart.CartScreen
import com.shops.ecomm.presentation.cart.CheckoutScreen
import com.shops.ecomm.presentation.cart.OrderSuccessScreen
import com.shops.ecomm.presentation.home.HomeScreen
import com.shops.ecomm.presentation.home.ProductDetailScreen
import com.shops.ecomm.presentation.onboarding.OnboardingScreen
import com.shops.ecomm.presentation.profile.ProfileScreen
import com.shops.ecomm.presentation.search.SearchScreen
import com.shops.ecomm.presentation.splash.SplashScreen
import com.shops.ecomm.presentation.wishlist.WishScreen
import com.shops.ecomm.presentation.auth.LoginScreen
import com.shops.ecomm.presentation.order.TrackOrderScreen
import com.shops.ecomm.domain.model.Product
import com.shops.ecomm.util.connectivityState
import com.shops.ecomm.ui.theme.EcommTheme

@Composable
fun MainScreen() {
    val isConnected by connectivityState()
    var isDarkMode by remember { mutableStateOf(false) }
    val navController = rememberNavController()
    val authViewModel: com.shops.ecomm.presentation.auth.AuthViewModel = androidx.hilt.navigation.compose.hiltViewModel()

    EcommTheme(darkTheme = isDarkMode) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                AnimatedVisibility(visible = !isConnected) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Red)
                            .statusBarsPadding()
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No Internet Connection",
                            color = Color.White,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                
                Box(modifier = Modifier.weight(1f)) {
                    NavHost(navController = navController, startDestination = "splash") {
                        composable("splash") {
                            SplashScreen { 
                                val startRoute = if (authViewModel.isUserLoggedIn()) "main_content" else "onboarding"
                                navController.navigate(startRoute) {
                                    popUpTo("splash") { inclusive = true }
                                }
                            }
                        }
                        composable("onboarding") {
                            OnboardingScreen { 
                                navController.navigate("login") {
                                    popUpTo("onboarding") { inclusive = true }
                                }
                            }
                        }
                        composable("login") {
                            LoginScreen(
                                onLoginSuccess = { 
                                    navController.navigate("main_content") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable("main_content") {
                            MainContent(
                                isDarkMode = isDarkMode,
                                onThemeToggle = { isDarkMode = it },
                                onProductClick = { product ->
                                    navigateToDetail(navController, product)
                                },
                                onCheckout = { total ->
                                    navController.navigate("checkout/$total")
                                },
                                onNavigateToTrackOrder = {
                                    navController.navigate("track_order")
                                },
                                onLogout = {
                                    authViewModel.resetState()
                                    navController.navigate("login") {
                                        popUpTo("main_content") { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable("track_order") {
                            TrackOrderScreen(onBack = { navController.popBackStack() })
                        }

                        composable(
                            route = "detail/{id}?title={title}&price={price}&image={image}&images={images}&description={description}",
                            arguments = listOf(
                                navArgument("id") { type = NavType.IntType },
                                navArgument("title") { 
                                    type = NavType.StringType 
                                    nullable = true
                                    defaultValue = ""
                                },
                                navArgument("price") { 
                                    type = NavType.StringType 
                                    nullable = true
                                    defaultValue = ""
                                },
                                navArgument("image") { 
                                    type = NavType.StringType 
                                    nullable = true
                                    defaultValue = ""
                                },
                                navArgument("images") { 
                                    type = NavType.StringType 
                                    nullable = true
                                    defaultValue = ""
                                },
                                navArgument("description") { 
                                    type = NavType.StringType 
                                    nullable = true
                                    defaultValue = ""
                                }
                            )
                        ) { backStack ->
                            val id = backStack.arguments?.getInt("id") ?: 0
                            val title = backStack.arguments?.getString("title") ?: ""
                            val price = backStack.arguments?.getString("price") ?: ""
                            val image = backStack.arguments?.getString("image") ?: ""
                            val imagesArg = backStack.arguments?.getString("images")
                            val images = if (!imagesArg.isNullOrEmpty()) {
                                imagesArg.split(",")
                            } else if (image.isNotEmpty()) {
                                listOf(image)
                            } else {
                                emptyList()
                            }
                            val description = backStack.arguments?.getString("description") ?: ""
                            ProductDetailScreen(
                                id = id,
                                title = title,
                                price = price,
                                images = images,
                                description = description,
                                onBack = { navController.popBackStack() },
                                onProductClick = { product ->
                                    navigateToDetail(navController, product)
                                },
                                onBuyNow = { totalPrice ->
                                    navController.navigate("checkout/$totalPrice")
                                }
                            )
                        }

                        composable(
                            route = "checkout/{total}",
                            arguments = listOf(navArgument("total") { type = NavType.IntType })
                        ) { backStack ->
                            val total = backStack.arguments?.getInt("total") ?: 0
                            CheckoutScreen(
                                totalAmount = total,
                                onBack = { navController.popBackStack() },
                                onOrderPlaced = { orderId ->
                                    navController.navigate("order_success/$orderId") {
                                        popUpTo("main_content") { inclusive = false }
                                    }
                                }
                            )
                        }

                        composable(
                            route = "order_success/{orderId}",
                            arguments = listOf(navArgument("orderId") { type = NavType.StringType })
                        ) { backStack ->
                            val orderId = backStack.arguments?.getString("orderId") ?: ""
                            OrderSuccessScreen(
                                orderId = orderId,
                                onContinueShopping = {
                                    navController.navigate("main_content") {
                                        popUpTo("splash") { inclusive = false }
                                    }
                                },
                                onTrackOrder = {
                                    navController.navigate("track_order") {
                                        popUpTo("order_success/$orderId") { inclusive = true }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


private fun navigateToDetail(
    navController: androidx.navigation.NavController,
    product: Product
) {

    val encodedTitle = Uri.encode(product.title)

    val firstImage = product.images.firstOrNull() ?: ""
    val encodedImage = Uri.encode(firstImage)

    val encodedDescription = Uri.encode(
        product.description
    )

    val encodedPrice = Uri.encode(
        product.price.toString()
    )

    val encodedImages = Uri.encode(
        product.images.joinToString(",")
    )


    navController.navigate(
        "detail/${product.id}?title=$encodedTitle&price=$encodedPrice&image=$encodedImage&images=$encodedImages&description=$encodedDescription"
    )
}


@Composable
fun MainContent(
    isDarkMode: Boolean,
    onThemeToggle: (Boolean) -> Unit,
    onProductClick: (Product) -> Unit,
    onCheckout: (Int) -> Unit,
    onNavigateToTrackOrder: () -> Unit,
    onLogout: () -> Unit
) {
    var selected by remember { mutableIntStateOf(0) }
    val cartViewModel: com.shops.ecomm.presentation.cart.CartViewModel = androidx.hilt.navigation.compose.hiltViewModel()
    val cartItems by cartViewModel.cartItems.collectAsState()
    val totalAmount = cartItems.sumOf { it.product.price * it.quantity }

    Scaffold(
        bottomBar = {
            BottomNav(
                selected = selected,
                cartCount = cartItems.size,
                onSelected = { selected = it }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = padding.calculateBottomPadding())
        ) {
            when (selected) {
                0 -> HomeScreen(
                    onProductClick = onProductClick,
                    onSearchClick = { selected = 1 }
                )
                1 -> SearchScreen(
                    onProductClick = onProductClick
                )
                2 -> WishScreen()
                3 -> CartScreen(
                    onCheckout = { onCheckout(totalAmount) }
                )
                4 -> ProfileScreen(
                    onNavigateToTrackOrder = onNavigateToTrackOrder,
                    isDarkMode = isDarkMode,
                    onThemeToggle = onThemeToggle,
                    onLogout = onLogout
                )
            }
        }
    }
}
