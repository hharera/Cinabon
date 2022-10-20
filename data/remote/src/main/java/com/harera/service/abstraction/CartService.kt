package com.harera.service.abstraction

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.harera.service.domain.ServiceDomainCartItem

interface CartService {

    fun removeCartItem(cartItemId: String): Task<Void>
    fun updateQuantity(cartItemId: String, quantity: Int): Task<Void>
    fun getCartItems(uid: String): Task<QuerySnapshot>
    fun addCartItem(serviceDomainCartItem: ServiceDomainCartItem): Task<Void>
    fun updateItemUid(itemId: String, uid: String): Task<Void>
    fun getCartItem(cartItemId: String): Task<QuerySnapshot>
    fun getCartItem(productId: String, uid: String): Task<QuerySnapshot>
}