package com.whiteside.cafe.model

class Category(
    var title: String,
    var drawableId: Int,
    var itemList: MutableList<Product?>
)