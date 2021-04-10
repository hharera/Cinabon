package com.whiteside.cafe.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Blob

class Offer {
    lateinit var categoryName: String
    lateinit var productId: String
    lateinit var offerId: String
    lateinit var startTime: Timestamp
    lateinit var endTime: Timestamp
    lateinit var offerPic: Blob
    var discountPercentage: Float? = null
    var type: Int? = null

//    constructor(
//        categoryName: String,
//        productId: String,
//        offerId: String,
//        startTime: Timestamp,
//        endTime: Timestamp,
//        offerPic: Blob,
//        discountPercentage: Float? = null,
//        type: Int? = null
//    ) : this() {
//        this.discountPercentage = discountPercentage
//        this.categoryName = categoryName
//        this.productId = productId
//        this.startTime = startTime
//        this.endTime = endTime
//        this.offerPic = offerPic
//        this.type = type
//        this.offerId = offerId
//    }
}