package com.whiteside.cafe.api.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.whiteside.cafe.model.Product

interface WishListRepository {

    fun addItemToUserWishList(product: Product): Task<Void>
    fun addWishListToProduct(product: Product): Task<Void>
    fun removeItemFromUserWishList(product: Product): Task<Void>
    fun removeWishListFromProduct(product: Product): Task<Void>
    fun getWishListItems(): Task<QuerySnapshot>
}