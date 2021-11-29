package com.harera.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity()
class CartItem {
    lateinit var uid: String
    lateinit var productId: String
    @PrimaryKey
    lateinit var cartItemId: String
    var quantity: Int = -1
    lateinit var time: Timestamp
    lateinit var productImageUrl: String
    lateinit var productTitle: String
    var productPrice: Float = -1f
}

