package com.whiteside.cafe.model

import com.google.firebase.firestore.Blob

class Product(
    private var mainPic: Blob,
    var productPics: MutableList<Blob>,
    var wishes: MutableMap<String, Int>,
    var carts: MutableMap<String, Int>,
    var productId: String,
    // TODO static list of category names
    var categoryName: String,
    var title: String,
    var price: Float,
)