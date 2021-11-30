package com.harera.model.modelget

import androidx.room.Entity
import androidx.room.PrimaryKey
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

