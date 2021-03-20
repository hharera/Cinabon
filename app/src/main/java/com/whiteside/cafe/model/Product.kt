package com.whiteside.cafe.model

import com.google.firebase.firestore.Blob

class Product {
    private var mainPic: Blob? = null
    private var productPics: MutableList<Blob?>? = null
    private var wishes: MutableMap<String?, Int?>? = null
    private var carts: MutableMap<String?, Int?>? = null
    private var productId: String? = null
    private var categoryName: String? = null
    private var title: String? = null
    private var price = 0f
    fun getProductId(): String? {
        return productId
    }

    fun setProductId(productId: String?) {
        this.productId = productId
    }

    fun getCategoryName(): String? {
        return categoryName
    }

    fun setCategoryName(categoryName: String?) {
        this.categoryName = categoryName
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getPrice(): Float {
        return price
    }

    fun setPrice(price: Float) {
        this.price = price
    }

    fun getMainPic(): Blob? {
        return mainPic
    }

    fun setMainPic(mainPic: Blob?) {
        this.mainPic = mainPic
    }

    fun getProductPics(): MutableList<Blob?>? {
        return productPics
    }

    fun setProductPics(productPics: MutableList<Blob?>?) {
        this.productPics = productPics
    }

    fun getWishes(): MutableMap<String?, Int?>? {
        return wishes
    }

    fun setWishes(wishes: MutableMap<String?, Int?>?) {
        this.wishes = wishes
    }

    fun getCarts(): MutableMap<String?, Int?>? {
        return carts
    }

    fun setCarts(carts: MutableMap<String?, Int?>?) {
        this.carts = carts
    }
}