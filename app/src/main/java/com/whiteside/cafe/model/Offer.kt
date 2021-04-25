package com.whiteside.cafe.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Blob
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Offer(
    var categoryName: String,
    var productId: String,
    var offerId: String,
    var startTime: Timestamp,
    var endTime: Timestamp,
    var offerPic: Blob,
    var discountPercentage: Float,
) {

    constructor() : this("", "", "",  Timestamp.now(),  Timestamp.now(), Blob.fromBytes(byteArrayOf()), 0f)

//    lateinit var categoryName: String
//    lateinit var productId: String
//    lateinit var offerId: String
//    lateinit var startTime: Timestamp
//    lateinit var endTime: Timestamp
//    lateinit var offerPic: Blob
//    var discountPercentage: Float? = null


}