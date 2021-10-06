package com.harera.model.modelset

data class Product(
    val productPictureUrls: ArrayList<String>,
    var productId: String? = null,
    val categoryName: String,
    val title: String,
    var price: Double,
    val amount: Double,
    val unit: String,
)