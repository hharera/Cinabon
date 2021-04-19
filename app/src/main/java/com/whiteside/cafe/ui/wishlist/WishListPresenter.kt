package com.whiteside.cafe.ui.wishlist

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.whiteside.cafe.model.Item
import com.whiteside.cafe.model.Product

class WishListPresenter {
    private val fStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var onGetWishListItem: OnGetWishListItem
    lateinit var onAddWishListItem: OnAddWishListItem
    lateinit var onRemoveWishListItemListener: OnRemoveWishListItemListener

    private fun getItemsFromFirebase() {
        val fStore = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        fStore.collection("Users")
            .document(auth.uid!!)
            .collection("WishList")
            .get()
            .addOnSuccessListener {
                for (ds in it.documents) {
                    onGetWishListItem.onGetWishListItemSuccess(ds.toObject(Item::class.java)!!)
                }
                onGetWishListItem.onWishListIsEmpty()

            }
            .addOnFailureListener {
                onGetWishListItem.onGetWishListItemFailed(it)
            }
    }

    fun getWishListItems() {
        getItemsFromFirebase()
    }

    fun addItem(product: Product) {
        val wishList = product.wishes
        wishList[auth.uid!!] = 1
        product.wishes = (wishList)
        val thread2: Thread = object : Thread() {
            override fun run() {
                fStore.collection("Categories")
                    .document(product.categoryName)
                    .collection("Products")
                    .document(product.productId)
                    .update("wishes", wishList)
                    .addOnSuccessListener { onAddWishListItem.onAddWishListItemSuccess() }
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
                    .collection("WishList")
                    .document(product.categoryName + product.productId)
                    .set(item)
                    .addOnSuccessListener { thread2.start() }
            }
        }
        thread1.start()
    }

    fun removeItem(product: Product) {
        val wishList = product.wishes
        wishList.remove(auth.uid)
        product.wishes = (wishList)
        val thread2: Thread = object : Thread() {
            override fun run() {
                fStore.collection("Categories")
                    .document(product.categoryName)
                    .collection("Products")
                    .document(product.productId)
                    .update("wishes", wishList)
                    .addOnSuccessListener { onRemoveWishListItemListener.onRemoveWishListItemSuccess() }
            }
        }
        val thread1: Thread = object : Thread() {
            override fun run() {
                fStore.collection("Users")
                    .document(auth.uid!!)
                    .collection("WishList")
                    .document(product.categoryName + product.productId)
                    .delete()
                    .addOnSuccessListener { thread2.start() }
            }
        }
        thread1.start()
    }
}