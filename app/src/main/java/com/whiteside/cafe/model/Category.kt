package com.whiteside.cafe.model

class Category {
    lateinit var title: String
    var drawableId: Int? = null
    lateinit var itemList: MutableList<Product?>
}