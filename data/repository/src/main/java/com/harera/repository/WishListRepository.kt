package com.harera.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.harera.repository.domain.WishItem

interface WishListRepository {

    fun addWishListItem(wishListItem: WishItem): Task<Void>
    fun removeWishListItem(productId: String, uid: String): Task<Void>
    fun getWishListItems(uid: String): Task<QuerySnapshot>
    fun updateItemUid(itemId: String, uid: String): Task<Void>
    fun getWishItem(productId: String, uid: String): Task<QuerySnapshot>
}