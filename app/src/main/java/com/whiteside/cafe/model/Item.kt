package com.whiteside.cafe.model

import com.google.firebase.Timestamp

class Item {
    lateinit var categoryName: String
    lateinit var productId: String
    var quantity: Int? = null
    lateinit var time: Timestamp
}
