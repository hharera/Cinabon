package com.whiteside.cafe.common.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.whiteside.cafe.common.repository.WishListRepository
import com.whiteside.cafe.model.Item
import com.whiteside.cafe.model.Product
import javax.inject.Inject

class FirebaseWishListRepository @Inject constructor() : WishListRepository {
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val fStore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    override fun getWishListItems(): Task<QuerySnapshot> =
        fStore.collection("Users")
            .document(auth.uid!!)
            .collection("WishList")
            .get()

//    override fun addItem(product: Product) {
//        addItemToUserWishList(product)
//        addWishListToProduct(product)
//    }
//
//    override fun removeItem(product: Product) {
//        removeWishListFromProduct(product)
//        return removeItemFromUserWishList(product)
//    }

    override fun addItemToUserWishList(product: Product): Task<Void> {
        val item = Item()
        item.let {
            it.categoryName = product.categoryName
            it.time = Timestamp.now()
            it.productId = product.productId
            it.quantity = 1
        }

        return fStore.collection("Users")
            .document(auth.uid!!)
            .collection("WishList")
            .document(product.categoryName + product.productId)
            .set(item)
    }

    override fun addWishListToProduct(product: Product): Task<Void> {
        product.wishes[auth.uid!!] = 1
        return fStore.collection("Categories")
            .document(product.categoryName)
            .collection("Products")
            .document(product.productId)
            .update("wishes", product.wishes)
    }

    override fun removeItemFromUserWishList(product: Product) =
        fStore.collection("Users")
            .document(auth.uid!!)
            .collection("WishList")
            .document(product.categoryName + product.productId)
            .delete()

    override fun removeWishListFromProduct(product: Product): Task<Void> {
        product.wishes.remove(auth.uid!!)
        return fStore.collection("Categories")
            .document(product.categoryName)
            .collection("Products")
            .document(product.productId)
            .update("wishes", product.wishes)
    }
}