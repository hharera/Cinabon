package com.whiteside.cafe.ui.wishList

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.whiteside.cafe.model.Item
import com.whiteside.cafe.model.Product

class WishListPresenter {
    private val fStore: FirebaseFirestore?
    private val auth: FirebaseAuth?
    var onGetWishListItem: OnGetWishListItem? = null
    var onAddWishListItem: OnAddWishListItem? = null
    var onRemoveWishListItemListener: OnRemoveWishListItemListener? = null
    fun setOnGetWishListItem(onGetWishListItem: OnGetWishListItem?) {
        this.onGetWishListItem = onGetWishListItem
    }

    fun setOnAddWishListItem(onAddWishListItem: OnAddWishListItem?) {
        this.onAddWishListItem = onAddWishListItem
    }

    fun setOnRemoveWishListItemListener(onRemoveWishListItemListener: OnRemoveWishListItemListener?) {
        this.onRemoveWishListItemListener = onRemoveWishListItemListener
    }

    private fun getItemsFromFirebase() {
        val fStore = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        fStore.collection("Users")
            .document(auth.uid)
            .collection("WishList")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result.isEmpty) {
                        onGetWishListItem.onWishListIsEmpty()
                    }
                    for (ds in task.result.documents) {
                        onGetWishListItem.onGetWishListItemSuccess(ds.toObject(Item::class.java))
                    }
                } else {
                    onGetWishListItem.onGetWishListItemFailed(task.exception)
                }
            }
    }

    fun getWishListItems() {
        getItemsFromFirebase()
    }

    fun addItem(product: Product?) {
        val wishList = product.getWishes()
        wishList[auth.getUid()] = 1
        product.setWishes(wishList)
        val thread2: Thread = object : Thread() {
            override fun run() {
                fStore.collection("Categories")
                    .document(product.getCategoryName())
                    .collection("Products")
                    .document(product.getProductId())
                    .update("wishes", wishList)
                    .addOnSuccessListener { onAddWishListItem.onAddWishListItemSuccess() }
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
                    .collection("WishList")
                    .document(product.getCategoryName() + product.getProductId())
                    .set(item)
                    .addOnSuccessListener { thread2.start() }
            }
        }
        thread1.start()
    }

    fun removeItem(product: Product?) {
        val wishList = product.getWishes()
        wishList.remove(auth.getUid())
        product.setWishes(wishList)
        val thread2: Thread = object : Thread() {
            override fun run() {
                fStore.collection("Categories")
                    .document(product.getCategoryName())
                    .collection("Products")
                    .document(product.getProductId())
                    .update("wishes", wishList)
                    .addOnSuccessListener { onRemoveWishListItemListener.onRemoveWishListItemSuccess() }
            }
        }
        val thread1: Thread = object : Thread() {
            override fun run() {
                fStore.collection("Users")
                    .document(auth.getUid())
                    .collection("WishList")
                    .document(product.getCategoryName() + product.getProductId())
                    .delete()
                    .addOnSuccessListener { thread2.start() }
            }
        }
        thread1.start()
    }

    init {
        fStore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
    }
}