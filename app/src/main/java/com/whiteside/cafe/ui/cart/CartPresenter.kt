package com.whiteside.cafe.ui.cart

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.whiteside.cafe.model.Item
import com.whiteside.cafe.model.Product

class CartPresenter {
    private var fStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var onGetCartItem: OnGetCartItem
    lateinit var onAddCartItem: OnAddCartItem
    lateinit var onRemoveCartItem: OnRemoveCartItem

    fun addItem(product: Product) {
        val carts = product.carts
        carts[auth.uid!!] = 1
        product.carts = (carts)
        val thread2: Thread = object : Thread() {
            override fun run() {
                fStore.collection("Categories")
                    .document(product.categoryName)
                    .collection("Products")
                    .document(product.productId)
                    .update("carts", carts)
                    .addOnSuccessListener { onAddCartItem.onAddCartItemSuccess() }
            }
        }
        val item = Item()
        item.let {
            it.categoryName = product.categoryName
            it.time = Timestamp.now()
            it.productId = product.productId
            it.quantity = 1
        }
        val thread1: Thread = object : Thread() {
            override fun run() {
                fStore.collection("Users")
                    .document(auth.uid!!)
                    .collection("Cart")
                    .document(product.categoryName + product.productId)
                    .set(item)
                    .addOnSuccessListener { thread2.start() }
            }
        }
        thread1.start()
    }

    fun removeItem(product: Product) {
        val carts = product.carts
        carts.remove(auth.uid)
        product.carts = (carts)
        val thread2: Thread = object : Thread() {
            override fun run() {
                fStore.collection("Categories")
                    .document(product.categoryName)
                    .collection("Products")
                    .document(product.productId)
                    .update("carts", carts)
                    .addOnSuccessListener { onRemoveCartItem.onRemoveCartItemSuccess() }
            }
        }
        val thread1: Thread = object : Thread() {
            override fun run() {
                fStore.collection("Users")
                    .document(auth.uid!!)
                    .collection("Cart")
                    .document(product.categoryName + product.productId)
                    .delete()
                    .addOnSuccessListener { thread2.start() }
            }
        }
        thread1.start()
    }

    private fun getItemsFromFirebase() {
        val fStore = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        fStore.collection("Users")
            .document(auth.uid!!)
            .collection("Cart")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result.isEmpty) {
                        onGetCartItem.onCartIsEmpty()
                    }
                    for (ds in task.result.documents) {
                        onGetCartItem.onGetCartItemSuccess(ds.toObject(Item::class.java)!!)
                    }
                } else {
                    onGetCartItem.onGetCartItemFailed(task.exception!!)
                }
            }
    }

    fun updateQuantity(item: Item, quantity: Int) {
        fStore.collection("Users")
            .document(auth.uid!!)
            .collection("Cart")
            .document(item.categoryName + item.productId)
            .update("quantity", quantity)
    }

    fun getCartItems() {
        getItemsFromFirebase()
    }

}