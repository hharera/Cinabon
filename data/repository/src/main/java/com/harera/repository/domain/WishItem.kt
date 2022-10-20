package com.harera.repository.domain

import com.google.firebase.Timestamp

class WishItem {
    lateinit var uid: String
    lateinit var productId: String
    var itemId : String = uid + productId
    lateinit var time: Timestamp
    lateinit var productImageUrl: String
    lateinit var productTitle: String
}
