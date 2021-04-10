package com.whiteside.cafe.model

import com.google.firebase.firestore.Blob

class Product {

    //    var mainPic: Blob? = null
    lateinit var productPics: ArrayList<Blob>
    lateinit var wishes: HashMap<String, Int>
    lateinit var carts: HashMap<String, Int>
    lateinit var productId: String

    // TODO static list of category names
    lateinit var categoryName: String
    lateinit var title: String
    var price: Float = 0.0f

}