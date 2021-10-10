package com.harera.model.modelset

import com.google.firebase.Timestamp

data class CartItem(
    var uid: String,
    var cartItemId: String? = null,
    var productId: String,
    var quantity: Int,
    var time: Timestamp
)
