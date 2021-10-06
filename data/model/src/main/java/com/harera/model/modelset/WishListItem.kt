package com.harera.model.modelset

import com.google.firebase.Timestamp

data class WishListItem(
    var uid: String,
    var productId: String,
    var time: Timestamp
)
