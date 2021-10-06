package com.harera.model.modelget

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class WishItem {
    lateinit var uid: String
    lateinit var productId: String
    lateinit var time: Timestamp
    lateinit var productImageUrl: String
    lateinit var productTitle: String
}
