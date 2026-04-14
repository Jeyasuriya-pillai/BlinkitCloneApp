package com.example.blinkit.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blinkit.components.ProductCard
import com.example.blinkit.data.*
import com.example.blinkit.ui.theme.*

@Composable
fun HomeScreen(
    onAddToCart: (Product) -> Unit,
    onRemoveFromCart: (Product) -> Unit,
    cartItems: List<CartItem>
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlinkitGray)
            .verticalScroll(scrollState)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(BlinkitYellow)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.LocationOn, contentDescription = null, tint = BlinkitBlack)
                    Spacer(modifier = Modifier.width(4.dp))
                    Column {
                        Text(
                            "Delivery in 10 minutes",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = BlinkitBlack
                        )
                        Text(
                            "Andheri West, Mumbai - 400058",
                            fontSize = 12.sp,
                            color = BlinkitDarkGray
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(Icons.Filled.Notifications, contentDescription = null, tint = BlinkitBlack)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Search, contentDescription = null, tint = BlinkitDarkGray)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Search for 'atta', 'dal', 'milk'...", color = BlinkitDarkGray, fontSize = 14.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(BlinkitGreen, Color(0xFF4CAF50))
                    )
                )
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Free Delivery", color = Color.White, fontSize = 12.sp)
                    Text("On your first\norder!", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .background(BlinkitYellow, RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text("Order Now", fontWeight = FontWeight.Bold, color = BlinkitBlack, fontSize = 13.sp)
                    }
                }
                Text("🛵", fontSize = 60.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Shop by Category",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = BlinkitBlack,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(SampleData.categories) { category ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(80.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(category.backgroundColor))
                        .clickable {}
                        .padding(10.dp)
                ) {
                    Image(
                        painter = painterResource(id = category.imageRes),
                        contentDescription = category.name,
                        modifier = Modifier.size(40.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = category.name,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = BlinkitBlack,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Fresh Vegetables & Fruits", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = BlinkitBlack)
            Text("See all", color = BlinkitGreen, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 10.dp)
        ) {
            items(SampleData.products) { product ->
                val cartItem = cartItems.find { it.product.id == product.id }
                val currentQty = cartItem?.quantity ?: 0

                ProductCard(
                    product = product,
                    quantity = currentQty,
                    onAdd = { onAddToCart(product) },
                    onRemove = { onRemoveFromCart(product) }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("⚡", fontSize = 30.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Blinkit delivers in 10 mins!", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text("Order groceries, fruits, veggies & more", fontSize = 12.sp, color = BlinkitDarkGray)
                }
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}