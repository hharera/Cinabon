package com.harera.repository.domain

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class Item {
    lateinit var categoryName: String
    lateinit var productId: String
    var quantity: Int? = null
    lateinit var time: Timestamp
}
