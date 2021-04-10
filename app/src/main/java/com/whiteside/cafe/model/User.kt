package com.whiteside.cafe.model

open class User {
    lateinit var name: String
    lateinit var phoneNumber: String
    lateinit var cartItems: MutableList<Item>
    lateinit var wishList: MutableList<Item>
    lateinit var uid: String
}