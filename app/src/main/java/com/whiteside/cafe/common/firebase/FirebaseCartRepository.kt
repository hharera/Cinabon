package com.whiteside.cafe.common.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.whiteside.cafe.common.repository.CartRepository
import com.whiteside.cafe.model.Item
import com.whiteside.cafe.model.Product
import javax.inject.Inject

class FirebaseCartRepository @Inject constructor() : CartRepository {
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val fStore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }


    override fun addItemToUserCart(product: Product): Task<Void> {
        val item = Item()
        item.let {
            it.categoryName = product.categoryName
            it.time = Timestamp.now()
            it.productId = product.productId
            it.quantity = 1
        }
        return fStore.collection("Users")
            .document(auth.uid!!)
            .collection("Cart")
            .document(product.categoryName + product.productId)
            .set(item)
    }

    override fun addCartToItem(product: Product): Task<Void> {
        product.carts[auth.uid!!] = 1

        return fStore.collection("Categories")
            .document(product.categoryName)
            .collection("Products")
            .document(product.productId)
            .update("carts", product.carts)
    }

    override fun removeItemFromUserCart(product: Product): Task<Void> =
        fStore.collection("Users")
            .document(auth.uid!!)
            .collection("Cart")
            .document(product.categoryName + product.productId)
            .delete()

    override fun removeCartFromProduct(product: Product): Task<Void> {
        product.carts.remove(auth.uid)

        return fStore.collection("Categories")
            .document(product.categoryName)
            .collection("Products")
            .document(product.productId)
            .update("carts", product.carts)
    }

    override fun updateQuantity(item: Item, quantity: Int): Task<Void> =
        fStore.collection("Users")
            .document(auth.uid!!)
            .collection("Cart")
            .document(item.categoryName + item.productId)
            .update("quantity", quantity)

    override fun getCartItems(): Task<QuerySnapshot> =
        fStore.collection("Users")
            .document(auth.uid!!)
            .collection("Cart")
            .get()
}