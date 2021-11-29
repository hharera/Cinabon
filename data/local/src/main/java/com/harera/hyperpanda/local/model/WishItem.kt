package com.harera.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity
class WishItem {
    lateinit var uid: String
    lateinit var productId: String
    @PrimaryKey
    var itemId : String = uid + productId
    lateinit var time: Timestamp
    lateinit var productImageUrl: String
    lateinit var productTitle: String
}
