package com.shops.ecomm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.shops.ecomm.presentation.home.HomeScreen
import com.shops.ecomm.presentation.home.ProductDetailScreen
import com.shops.ecomm.presentation.search.SearchScreen
import com.shops.ecomm.domain.model.Product
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import android.net.Uri

sealed class Screen(val route: String) {

    object Home : Screen("home")

    object Search : Screen("search")

    object Detail : Screen("detail/{id}?title={title}&price={price}&image={image}&description={description}") {
        fun create(id: Int, title: String, price: String, image: String, description: String): String {
            val encodedTitle = Uri.encode(title)
            val encodedImage = Uri.encode(image)
            val encodedDescription = Uri.encode(description)
            return "detail/$id?title=$encodedTitle&price=$price&image=$encodedImage&description=$encodedDescription"
        }
    }
}

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        // HOME SCREEN
        composable(Screen.Home.route) {

            HomeScreen(
                onProductClick = { product ->

                    navController.navigate(
                        Screen.Detail.create(
                            product.id,
                            product.title,
                            product.price.toString(),
                            product.images.firstOrNull() ?: "",
                            product.description
                        )
                    )

                },
                onSearchClick = {
                    navController.navigate(Screen.Search.route)
                }
            )
        }

        // SEARCH SCREEN
        composable(Screen.Search.route) {
            SearchScreen(
                onProductClick = { product ->
                    navController.navigate(
                        Screen.Detail.create(
                            product.id,
                            product.title,
                            product.price.toString(),
                            product.images.firstOrNull() ?: "",
                            product.description
                        )
                    )
                }
            )
        }

        //  DETAIL SCREEN
        composable(
            route = Screen.Detail.route,
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
            val description = backStack.arguments?.getString("description") ?: ""

            ProductDetailScreen(
                id = id,
                title = title,
                price = price,
                images = listOf(image),
                description = description,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
