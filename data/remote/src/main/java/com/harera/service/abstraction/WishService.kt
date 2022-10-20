package com.harera.service.abstraction

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.harera.service.domain.ServiceDomainWishItem

interface WishService {

    fun addWishListItem(wishListItem: ServiceDomainWishItem): Task<Void>
    fun removeWishListItem(productId: String, uid: String): Task<Void>
    fun getWishListItems(uid: String): Task<QuerySnapshot>
    fun updateItemUid(itemId: String, uid: String): Task<Void>
    fun getWishItem(productId: String, uid: String): Task<QuerySnapshot>
}