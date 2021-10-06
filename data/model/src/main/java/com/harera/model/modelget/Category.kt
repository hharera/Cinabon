package com.harera.model.modelget

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class Category {
    lateinit var  categoryName : String
    lateinit var categoryId : String
    lateinit var categoryImagerUrl : String
}