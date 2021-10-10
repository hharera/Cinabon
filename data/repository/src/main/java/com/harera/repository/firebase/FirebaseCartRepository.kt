package com.harera.repository.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.harera.model.modelset.CartItem
import com.harera.repository.DBConstants.CART
import com.harera.repository.repository.CartRepository
import javax.inject.Inject

class FirebaseCartRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val fStore: FirebaseFirestore
) : CartRepository {

    override fun addCartItem(cartItem: CartItem) =
        fStore
            .collection(CART)
            .document("${cartItem.productId}${cartItem.uid}").apply {
                cartItem.cartItemId = id
            }
            .set(cartItem)

    override fun removeCartItem(itemItemId: String): Task<Void> =
        fStore
            .collection(CART)
            .document(itemItemId)
            .delete()

    override fun updateQuantity(itemItemId: String, quantity: Int) =
        fStore
            .collection(CART)
            .document(itemItemId)
            .update(CartItem::quantity.name, quantity)

    override fun updateItemUid(itemId: String, uid: String): Task<Void> =
        fStore
            .collection(CART)
            .document(itemId)
            .update(CartItem::uid.name, uid)

    override fun getCartItem(cartItemId: String): Task<QuerySnapshot> =
        fStore
            .collection(CART)
            .whereEqualTo(CartItem::cartItemId.name, cartItemId)
            .get()

    override fun getCartItem(productId: String, uid: String): Task<QuerySnapshot> =
        fStore
            .collection(CART)
            .whereEqualTo(CartItem::productId.name, productId)
            .whereEqualTo(CartItem::uid.name, uid)
            .get()

    override fun getCartItems(uid: String): Task<QuerySnapshot> =
        fStore
            .collection(CART)
            .whereEqualTo(CartItem::uid.name, uid)
            .get()
}