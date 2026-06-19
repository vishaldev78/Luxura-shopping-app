package com.shops.ecomm.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector  // Added unselectedIcon property
)