package com.example.blinkit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.blinkit.data.*
import com.example.blinkit.screens.*
import com.example.blinkit.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlinkitTheme {
                var selectedTab by remember { mutableIntStateOf(0) }
                var cartItems by remember { mutableStateOf(listOf<CartItem>()) }
                val ordersList = remember { mutableStateListOf<Order>() } // Order History

                fun addToCart(product: Product) {
                    val existing = cartItems.find { it.product.id == product.id }
                    cartItems = if (existing != null) {
                        cartItems.map { if (it.product.id == product.id) it.copy(quantity = it.quantity + 1) else it }
                    } else { cartItems + CartItem(product, 1) }
                }

                fun decrementCart(product: Product) {
                    cartItems = cartItems.mapNotNull {
                        if (it.product.id == product.id) {
                            if (it.quantity > 1) it.copy(quantity = it.quantity - 1) else null
                        } else it
                    }
                }

                fun placeOrder(total: Int) {
                    val newOrder = Order(
                        orderId = "#BK${(1000..9999).random()}",
                        items = cartItems.toList(),
                        totalAmount = total,
                        date = "Today, 10:45 AM"
                    )
                    ordersList.add(0, newOrder)
                    cartItems = emptyList()
                    selectedTab = 3
                }

                Scaffold(
                    bottomBar = {
                        NavigationBar(containerColor = Color.White) {
                            val items = listOf("Home", "Search", "Cart", "Account")
                            val icons = listOf(Icons.Default.Home, Icons.Default.Search, Icons.Default.ShoppingCart, Icons.Default.Person)
                            items.forEachIndexed { index, label ->
                                NavigationBarItem(
                                    selected = selectedTab == index,
                                    onClick = { selectedTab = index },
                                    label = { Text(label) },
                                    icon = {
                                        if (index == 2 && cartItems.isNotEmpty()) {
                                            BadgedBox(badge = { Badge { Text("${cartItems.sumOf { it.quantity }}") } }) {
                                                Icon(icons[index], null)
                                            }
                                        } else { Icon(icons[index], null) }
                                    },
                                    colors = NavigationBarItemDefaults.colors(selectedIconColor = BlinkitGreen, indicatorColor = Color(0xFFE8F5E9))
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        when (selectedTab) {
                            0 -> HomeScreen(
                                cartItems = cartItems,
                                onAddToCart = { addToCart(it) },
                                onRemoveFromCart = { decrementCart(it) }
                            )
                            1 -> SearchScreen(
                                cartItems = cartItems,
                                onAddToCart = { addToCart(it) },
                                onRemoveFromCart = { decrementCart(it) }
                            )
                            2 -> CartScreen(
                                cartItems = cartItems,
                                onIncrement = { addToCart(it.product) },
                                onDecrement = { decrementCart(it.product) },
                                onRemove = { item -> cartItems = cartItems.filter { it.product.id != item.product.id } },
                                onClearCart = { placeOrder(it) }
                            )
                            3 -> ProfileScreen(orders = ordersList)
                        }
                    }
                }
            }
        }
    }
}