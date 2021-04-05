package com.whiteside.cafe.model

open class User(
    var name: String,
    var phoneNumber: String,
    var cartItems: MutableList<Item>,
    var wishList: MutableList<Item>,
    var uid: String
)