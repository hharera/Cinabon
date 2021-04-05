package com.whiteside.cafe.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Blob

class Offer() {

    var categoryName: String? = null
    var productId: String? = null
    var offerId: String? = null
    var startTime: Timestamp? = null
    var endTime: Timestamp? = null
    var offerPic: Blob? = null
    var discountPercentage: Float? = null
    var type: Int? = null

    constructor(
        categoryName: String,
        productId: String,
        offerId: String? = null,
        startTime: Timestamp,
        endTime: Timestamp,
        offerPic: Blob,
        discountPercentage: Float,
        type: Int,
    ) : this() {
        this.categoryName = categoryName
        this.discountPercentage = discountPercentage
        this.productId = productId
        this.startTime = startTime
        this.offerPic = offerPic
        this.type = type
        this.endTime = endTime
        this.offerId = offerId
    }
}