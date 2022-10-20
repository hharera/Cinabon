package com.harera.hyperpanda.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity
class OfferEntity {
    lateinit var categoryName: String
    lateinit var offerType: String
    lateinit var productId: String
    @PrimaryKey
    lateinit var offerId: String
    lateinit var offerTitle: String
    lateinit var offerImageUrl: String
    lateinit var startTime: Timestamp
    lateinit var endTime: Timestamp
    var type: Int? = null
}