package com.whiteside.cafe.api.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.whiteside.cafe.model.Item
import com.whiteside.cafe.model.Product

interface CartRepository {

    fun addItemToUserCart(product: Product): Task<Void>
    fun addCartToItem(product: Product): Task<Void>
    fun removeItemFromUserCart(product: Product): Task<Void>
    fun removeCartFromProduct(product: Product): Task<Void>
    fun updateQuantity(item: Item, quantity: Int): Task<Void>
    fun getCartItems(): Task<QuerySnapshot>
}