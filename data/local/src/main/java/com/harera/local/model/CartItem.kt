package com.harera.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.security.Timestamp


@Entity
class CartItem {
    @PrimaryKey
    lateinit var cartItemId: String
    lateinit var uid: String
    lateinit var productId: String
    var quantity: Int = -1
    lateinit var time: Timestamp
    lateinit var productImageUrl: String
    lateinit var productTitle: String
    var productPrice: Float = -1f
}
