package com.example.blinkit.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import com.example.blinkit.ui.theme.BlinkitGreen

@Composable
fun BottomNavBar(selectedIndex: Int, onItemSelected: (Int) -> Unit) {
    val items = listOf("Home", "Search", "Cart", "Account")
    val icons = listOf(
        Icons.Filled.Home,
        Icons.Filled.Search,
        Icons.Filled.ShoppingCart,
        Icons.Filled.Person
    )

    NavigationBar(containerColor = Color.White) {
        items.forEachIndexed { index, label ->
            NavigationBarItem(
                icon = { Icon(icons[index], contentDescription = label) },
                label = { Text(label) },
                selected = selectedIndex == index,
                onClick = { onItemSelected(index) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = BlinkitGreen,
                    selectedTextColor = BlinkitGreen,
                    indicatorColor = Color(0xFFE8F5E9)
                )
            )
        }
    }
}