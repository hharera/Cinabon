package com.harera.model.modelget

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
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
