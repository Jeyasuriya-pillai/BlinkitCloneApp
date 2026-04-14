package com.example.blinkit.screens

import androidx.compose.animation.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blinkit.data.Product
import com.example.blinkit.data.CartItem
import com.example.blinkit.data.SampleData
import com.example.blinkit.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onAddToCart: (Product) -> Unit,
    onRemoveFromCart: (Product) -> Unit,
    cartItems: List<CartItem>
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    val categories = listOf("All", "Vegetables", "Fruits", "Dairy", "Munchies", "Instant", "Drinks", "Tea")

    val filteredProducts = SampleData.products.filter { product ->
        val matchesSearch = product.name.contains(searchQuery, ignoreCase = true) ||
                product.category.contains(searchQuery, ignoreCase = true)
        val matchesCategory = selectedCategory == "All" || product.category == selectedCategory
        matchesSearch && matchesCategory
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlinkitGray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(BlinkitYellow)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search groceries, fruits, dairy...") },
                leadingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = null, tint = BlinkitDarkGray)
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Filled.Close, contentDescription = null)
                        }
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = BlinkitGreen,
                    unfocusedBorderColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = { selectedCategory = category },
                    label = { Text(category, fontSize = 13.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = BlinkitGreen,
                        selectedLabelColor = Color.White,
                        containerColor = BlinkitGray
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        if (filteredProducts.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🔍", fontSize = 60.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "No results for \"$searchQuery\"",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = BlinkitBlack
                    )
                    Text(
                        "Try searching something else",
                        color = BlinkitDarkGray,
                        fontSize = 14.sp
                    )
                }
            }
        } else {
            Text(
                "${filteredProducts.size} results found",
                fontSize = 13.sp,
                color = BlinkitDarkGray,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredProducts) { product ->
                    val cartItem = cartItems.find { it.product.id == product.id }
                    val currentQty = cartItem?.quantity ?: 0
                    SearchProductRow(
                        product = product,
                        quantity = currentQty,
                        onAddToCart = { onAddToCart(product) },
                        onRemoveFromCart = { onRemoveFromCart(product) }
                    )
                }
            }
        }
    }
}

@Composable
fun SearchProductRow(
    product: Product,
    quantity: Int,
    onAddToCart: () -> Unit,
    onRemoveFromCart: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(65.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(BlinkitGray),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize().padding(8.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(product.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(product.unit, color = BlinkitDarkGray, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("₹${product.price}", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = BlinkitBlack)
                    if (product.discount > 0) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .background(BlinkitGreen, RoundedCornerShape(4.dp))
                                .padding(horizontal = 5.dp, vertical = 2.dp)
                        ) {
                            Text("${product.discount}% OFF", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            if (quantity == 0) {
                OutlinedButton(
                    onClick = { onAddToCart() },
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.5.dp, BlinkitGreen),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = BlinkitGreen),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text("ADD", fontWeight = FontWeight.Bold)
                }
            } else {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(BlinkitGreen)
                        .padding(horizontal = 4.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { onRemoveFromCart() }, modifier = Modifier.size(28.dp)) {
                        Text("−", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                    Text("$quantity", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp))
                    IconButton(onClick = { onAddToCart() }, modifier = Modifier.size(28.dp)) {
                        Text("+", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }
            }
        }
    }
}
