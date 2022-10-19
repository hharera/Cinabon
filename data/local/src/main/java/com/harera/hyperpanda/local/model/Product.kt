package com.harera.hyperpanda.local.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class Product {
    @Ignore
    lateinit var productPictureUrls: List<String>
    @PrimaryKey
    lateinit var productId: String
    lateinit var categoryName: String
    lateinit var title: String
    var price: Double = 0.0
    var amount: Double = 0.0
    lateinit var unit: String
}
