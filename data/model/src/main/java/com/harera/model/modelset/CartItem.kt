package com.harera.model.modelset

import com.google.firebase.Timestamp

data class CartItem(
    var uid: String,
    var productId: String,
    var quantity: Int,
    var time: Timestamp
)
