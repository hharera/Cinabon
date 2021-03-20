package com.whiteside.cafe.model

class FirebaseUser : User() {
    private var cartItems: MutableList<Item?>? = null
    private var wishList: MutableList<Item?>? = null
    fun getCartItems(): MutableList<Item?>? {
        return cartItems
    }

    fun setCartItems(cartItems: MutableList<Item?>?) {
        this.cartItems = cartItems
    }

    fun getWishList(): MutableList<Item?>? {
        return wishList
    }

    fun setWishList(wishList: MutableList<Item?>?) {
        this.wishList = wishList
    }
}