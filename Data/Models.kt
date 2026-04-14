package com.example.blinkit.data

import com.example.blinkit.R

data class Product(
    val id: Int,
    val name: String,
    val price: Int,
    val originalPrice: Int,
    val imageRes: Int,
    val unit: String,
    val discount: Int,
    val category: String
)

data class CartItem(
    val product: Product,
    var quantity: Int
)

data class Order(
    val orderId: String,
    val items: List<CartItem>,
    val totalAmount: Int,
    val date: String
)

data class Category(
    val name: String,
    val imageRes: Int,
    val backgroundColor: Long
)

object SampleData {
    val categories = listOf(
        Category("Vegetables", R.drawable.peas, 0xFFE8F5E9),
        Category("Fruits", R.drawable.mango, 0xFFFFF3E0),
        Category("Drinks", R.drawable.pepsi_combo, 0xFFE3F2FD),
        Category("Munchies", R.drawable.feastables, 0xFFF3E5F5)
    )

    val products = listOf(
        Product(1, "Pepsi Combo", 499, 600, R.drawable.pepsi_combo, "1 Set", 17, "Drinks"),
        Product(2, "Banana", 60, 80, R.drawable.banana, "6 pcs", 25, "Fruits"),
        Product(3, "Carrots", 40, 50, R.drawable.carrots, "500 g", 20, "Vegetables"),
        Product(4, "Cauliflower", 35, 45, R.drawable.cauliflower, "1 pc", 22, "Vegetables"),
        Product(5, "Sweet Corn", 25, 30, R.drawable.corn, "1 pc", 16, "Vegetables"),
        Product(6, "Cucumber", 30, 40, R.drawable.cucumber, "500 g", 25, "Vegetables"),
        Product(7, "Eggplant", 30, 35, R.drawable.eggplant, "500 g", 14, "Vegetables"),
        Product(8, "Feastables", 199, 250, R.drawable.feastables, "1 bar", 20, "Munchies"),
        Product(9, "Mango", 200, 300, R.drawable.mango, "1 kg", 33, "Fruits"),
        Product(10, "Orange", 80, 100, R.drawable.orange, "1 kg", 20, "Fruits"),
        Product(11, "Green Peas", 40, 60, R.drawable.peas, "500 g", 33, "Vegetables"),
        Product(12, "Potatoes", 30, 40, R.drawable.potato, "1 kg", 25, "Vegetables"),
        Product(13, "Radish", 20, 25, R.drawable.radish, "500 g", 20, "Vegetables")
    )
}