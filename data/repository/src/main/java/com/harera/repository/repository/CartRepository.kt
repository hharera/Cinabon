package com.harera.repository.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.harera.model.modelset.CartItem

interface CartRepository {

    fun removeCartItem(itemId: String): Task<Void>
    fun updateQuantity(itemId: String, uid: String, quantity: Int): Task<Void>
    fun getCartItems(uid: String): Task<QuerySnapshot>
    fun addCartItem(cartItem: CartItem): Task<Void>
    fun updateItemUid(itemId: String, uid: String): Task<Void>
    fun getCartItem(productId: String, uid: String): Task<QuerySnapshot>
    fun removeCartItem(productId: String, uid: String): Task<Void>
}