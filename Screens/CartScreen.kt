package com.example.blinkit.screens

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blinkit.data.CartItem
import com.example.blinkit.ui.theme.*

@Composable
fun CartScreen(
    cartItems: List<CartItem>,
    onIncrement: (CartItem) -> Unit,
    onDecrement: (CartItem) -> Unit,
    onRemove: (CartItem) -> Unit,
    onClearCart: (Int) -> Unit
) {
    val context = LocalContext.current
    val subtotal = cartItems.sumOf { it.product.price * it.quantity }
    val deliveryFee = if (subtotal > 199 || subtotal == 0) 0 else 25
    val total = subtotal + deliveryFee

    Column(modifier = Modifier.fillMaxSize().background(BlinkitGray)) {
        // Header
        Box(modifier = Modifier.fillMaxWidth().background(BlinkitYellow).padding(16.dp)) {
            Text("My Cart 🛒", fontWeight = FontWeight.Bold, fontSize = 22.sp)
        }

        if (cartItems.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Your cart is empty!", color = BlinkitDarkGray)
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f).padding(16.dp)) {
                items(cartItems) { item ->
                    CartProductRow(item, onIncrement, onDecrement, onRemove)
                }

                item {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Bill Details", fontWeight = FontWeight.Bold)
                            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                                Text("Item Total")
                                Text("₹$subtotal")
                            }
                            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                                Text("Delivery Fee")
                                Text(if (deliveryFee == 0) "FREE" else "₹$deliveryFee", color = BlinkitGreen)
                            }
                            HorizontalDivider(Modifier.padding(vertical = 8.dp))
                            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                                Text("Grand Total", fontWeight = FontWeight.Bold)
                                Text("₹$total", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // Place Order Button
            Button(
                onClick = {
                    Toast.makeText(context, "Order Placed Successfully! 🛵", Toast.LENGTH_LONG).show()
                    onClearCart(total) // Change: total pass ho raha hai MainActivity ko
                },
                modifier = Modifier.fillMaxWidth().padding(16.dp).height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BlinkitGreen),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Place Order • ₹$total", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@Composable
fun CartProductRow(
    item: CartItem,
    onIncrement: (CartItem) -> Unit,
    onDecrement: (CartItem) -> Unit,
    onRemove: (CartItem) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = item.product.imageRes),
                contentDescription = item.product.name,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Fit
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(item.product.name, fontWeight = FontWeight.Bold)
                Text("₹${item.product.price}", color = BlinkitDarkGray)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onDecrement(item) }) { Text("-", fontWeight = FontWeight.Bold) }
                Text("${item.quantity}", fontWeight = FontWeight.Bold)
                IconButton(onClick = { onIncrement(item) }) { Text("+", fontWeight = FontWeight.Bold) }
                IconButton(onClick = { onRemove(item) }) {
                    Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                }
            }
        }
    }
}