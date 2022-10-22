package com.harera.service.impl

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.harera.service.DBConstants
import com.harera.service.WishService
import com.harera.service.domain.ServiceDomainCartItem
import com.harera.service.domain.ServiceDomainWishItem
import javax.inject.Inject

class WishServiceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val fStore: FirebaseFirestore
) : WishService {

    override fun addWishListItem(wishListItem: ServiceDomainWishItem) =
        fStore
            .collection(DBConstants.WISHLIST)
            .document("${wishListItem.productId}${wishListItem.uid}")
            .set(wishListItem)

    override fun removeWishListItem(productId: String, uid: String): Task<Void> =
        fStore
            .collection(DBConstants.WISHLIST)
            .document("$productId$uid")
            .delete()

    override fun getWishListItems(uid: String) =
        fStore
            .collection(DBConstants.WISHLIST)
            .whereEqualTo(ServiceDomainCartItem::uid.name, uid)
            .get()

    override fun updateItemUid(itemId: String, uid: String): Task<Void> =
        fStore
            .collection(DBConstants.WISHLIST)
            .document(itemId)
            .update(ServiceDomainCartItem::uid.name, uid)

    override fun getWishItem(productId: String, uid: String) =
        fStore
            .collection(DBConstants.WISHLIST)
            .whereEqualTo(ServiceDomainWishItem::productId.name, productId)
            .whereEqualTo(ServiceDomainWishItem::uid.name, uid)
            .get()
}