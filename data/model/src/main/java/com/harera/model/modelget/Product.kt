package com.harera.model.modelget

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class Product {
    lateinit var productPictureUrls: ArrayList<String>
    lateinit var productId: String
    lateinit var categoryName: String
    lateinit var title: String
    var price: Double = -1.0
    var amount: Double = -1.0
    lateinit var unit: String
}
