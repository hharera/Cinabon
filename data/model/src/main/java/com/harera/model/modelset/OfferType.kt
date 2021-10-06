package com.harera.model.modelset

import com.google.firebase.Timestamp

data class OfferType(
    val startTime: Timestamp,
    val endTime: Timestamp,
    val offerTitle: String,
    val offerTypeId: String,
    val offerImageUrl: String,
)
