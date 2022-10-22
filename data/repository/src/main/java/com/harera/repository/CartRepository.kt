package com.harera.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.harera.repository.domain.CartItem

interface CartRepository {

    fun removeCartItem(cartItemId: String): Task<Void>
    fun updateQuantity(cartItemId: String, quantity: Int): Task<Void>
    fun getCartItems(uid: String): Task<QuerySnapshot>
    fun addCartItem(cartItem: CartItem): Task<Void>
    fun updateItemUid(itemId: String, uid: String): Task<Void>
    fun getCartItem(cartItemId: String): Task<QuerySnapshot>
    fun getCartItem(productId: String, uid: String): Task<QuerySnapshot>
}