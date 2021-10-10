package com.harera.model.modelget

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class CartItem {
    lateinit var uid: String
    lateinit var productId: String
    lateinit var cartItemId: String
    var quantity: Int = -1
    lateinit var time: Timestamp
    lateinit var productImageUrl: String
    lateinit var productTitle: String
    var productPrice: Float = -1f
}

