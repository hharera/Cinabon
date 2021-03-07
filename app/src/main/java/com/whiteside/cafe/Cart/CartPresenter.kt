package com.whiteside.cafe.Cart

import Model.Item
import Model.Product
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CartPresenter {
    private val fStore: FirebaseFirestore?
    private val auth: FirebaseAuth?
    var onGetCartItem: OnGetCartItem? = null
    var onAddCartItem: OnAddCartItem? = null
    var onRemoveCartItem: OnRemoveCartItem? = null
    fun setOnGetCartItem(onGetCartItem: OnGetCartItem?) {
        this.onGetCartItem = onGetCartItem
    }

    fun setOnAddCartItem(onAddCartItem: OnAddCartItem?) {
        this.onAddCartItem = onAddCartItem
    }

    fun setOnRemoveCartItem(onRemoveCartItem: OnRemoveCartItem?) {
        this.onRemoveCartItem = onRemoveCartItem
    }

    fun addItem(product: Product?) {
        val carts = product.getCarts()
        carts[auth.getUid()] = 1
        product.setCarts(carts)
        val thread2: Thread = object : Thread() {
            override fun run() {
                fStore.collection("Categories")
                    .document(product.getCategoryName())
                    .collection("Products")
                    .document(product.getProductId())
                    .update("carts", carts)
                    .addOnSuccessListener { onAddCartItem.onAddCartItemSuccess() }
            }
        }
        val item = Item()
        item.time = Timestamp.now()
        item.categoryName = product.getCategoryName()
        item.productId = product.getProductId()
        item.quantity = 1
        val thread1: Thread = object : Thread() {
            override fun run() {
                fStore.collection("Users")
                    .document(auth.getUid())
                    .collection("Cart")
                    .document(product.getCategoryName() + product.getProductId())
                    .set(item)
                    .addOnSuccessListener { thread2.start() }
            }
        }
        thread1.start()
    }

    fun removeItem(product: Product?) {
        val carts = product.getCarts()
        carts.remove(auth.getUid())
        product.setCarts(carts)
        val thread2: Thread = object : Thread() {
            override fun run() {
                fStore.collection("Categories")
                    .document(product.getCategoryName())
                    .collection("Products")
                    .document(product.getProductId())
                    .update("carts", carts)
                    .addOnSuccessListener { onRemoveCartItem.onRemoveCartItemSuccess() }
            }
        }
        val thread1: Thread = object : Thread() {
            override fun run() {
                fStore.collection("Users")
                    .document(auth.getUid())
                    .collection("Cart")
                    .document(product.getCategoryName() + product.getProductId())
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
            .document(auth.uid)
            .collection("Cart")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result.isEmpty) {
                        onGetCartItem.onCartIsEmpty()
                    }
                    for (ds in task.result.documents) {
                        onGetCartItem.onGetCartItemSuccess(ds.toObject(Item::class.java))
                    }
                } else {
                    onGetCartItem.onGetCartItemFailed(task.exception)
                }
            }
    }

    fun updateQuantity(item: Item?, quantity: Int) {
        fStore.collection("Users")
            .document(auth.getUid())
            .collection("Cart")
            .document(item.getCategoryName() + item.getProductId())
            .update("quantity", quantity)
    }

    fun getCartItems() {
        getItemsFromFirebase()
    }

    init {
        fStore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
    }
}