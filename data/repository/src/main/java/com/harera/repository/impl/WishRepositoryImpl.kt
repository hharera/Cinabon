package com.harera.repository.impl

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.harera.repository.abstraction.DBConstants
import com.harera.repository.abstraction.WishListRepository
import com.harera.repository.domain.CartItem
import com.harera.repository.domain.WishItem
import javax.inject.Inject

class WishRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val fStore: FirebaseFirestore
) : WishListRepository {

    override fun addWishListItem(wishListItem: WishItem) =
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
            .whereEqualTo(CartItem::uid.name, uid)
            .get()

    override fun updateItemUid(itemId: String, uid: String): Task<Void> =
        fStore
            .collection(DBConstants.WISHLIST)
            .document(itemId)
            .update(CartItem::uid.name, uid)

    override fun getWishItem(productId: String, uid: String) =
        fStore
            .collection(DBConstants.WISHLIST)
            .whereEqualTo(WishItem::productId.name, productId)
            .whereEqualTo(WishItem::uid.name, uid)
            .get()
}