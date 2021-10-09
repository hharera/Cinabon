package com.harera.repository.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.harera.model.modelset.CartItem
import com.harera.model.modelset.WishListItem
import com.harera.repository.DBConstants
import com.harera.repository.repository.WishListRepository
import javax.inject.Inject

class FirebaseWishListRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val fStore: FirebaseFirestore
) : WishListRepository {

    override fun addWishListItem(wishListItem: WishListItem) =
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
            .whereEqualTo(WishListItem::productId.name, productId)
            .whereEqualTo(WishListItem::uid.name, uid)
            .get()
}