package com.harera.model.modelget

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class Offer {
    lateinit var categoryName: String
    lateinit var offerType: String
    lateinit var productId: String
    lateinit var offerId: String
    lateinit var offerTitle: String
    lateinit var offerImageUrl: String
    lateinit var startTime: Timestamp
    lateinit var endTime: Timestamp
    var type: Int? = null
}