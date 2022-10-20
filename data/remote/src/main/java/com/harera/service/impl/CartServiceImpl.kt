package com.harera.service.impl

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.harera.service.abstraction.CartService
import com.harera.service.abstraction.DBConstants.CART
import com.harera.service.domain.ServiceDomainCartItem
import javax.inject.Inject

class CartServiceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val fStore: FirebaseFirestore
) : CartService {

    override fun addCartItem(serviceDomainCartItem: ServiceDomainCartItem) =
        fStore
            .collection(CART)
            .document("${serviceDomainCartItem.productId}${serviceDomainCartItem.uid}").apply {
                serviceDomainCartItem.cartItemId = id
            }
            .set(serviceDomainCartItem)

    override fun removeCartItem(itemItemId: String): Task<Void> =
        fStore
            .collection(CART)
            .document(itemItemId)
            .delete()

    override fun updateQuantity(itemItemId: String, quantity: Int) =
        fStore
            .collection(CART)
            .document(itemItemId)
            .update(ServiceDomainCartItem::quantity.name, quantity)

    override fun updateItemUid(itemId: String, uid: String): Task<Void> =
        fStore
            .collection(CART)
            .document(itemId)
            .update(ServiceDomainCartItem::uid.name, uid)

    override fun getCartItem(cartItemId: String): Task<QuerySnapshot> =
        fStore
            .collection(CART)
            .whereEqualTo(ServiceDomainCartItem::cartItemId.name, cartItemId)
            .get()

    override fun getCartItem(productId: String, uid: String): Task<QuerySnapshot> =
        fStore
            .collection(CART)
            .whereEqualTo(ServiceDomainCartItem::productId.name, productId)
            .whereEqualTo(ServiceDomainCartItem::uid.name, uid)
            .get()

    override fun getCartItems(uid: String): Task<QuerySnapshot> =
        fStore
            .collection(CART)
            .whereEqualTo(ServiceDomainCartItem::uid.name, uid)
            .get()
}