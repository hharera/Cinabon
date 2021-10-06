package com.harera.model.modelset

import com.google.firebase.Timestamp

data class Offer(
    val productId: String,
    var offerId: String? = null,
    val startTime: Timestamp,
    val endTime: Timestamp,
    val offerImageUrl: String,
    val offerTitle: String,
    val offerTypeId: String,
)
