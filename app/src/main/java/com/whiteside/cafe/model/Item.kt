package com.whiteside.cafe.model

import com.google.firebase.Timestamp

class Item(
    var categoryName: String,
    var productId: String,
    var quantity: Int,
    var time: Timestamp,
)
