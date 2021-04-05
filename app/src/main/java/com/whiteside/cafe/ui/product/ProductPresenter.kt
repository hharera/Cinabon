package com.whiteside.cafe.ui.product

import com.google.firebase.firestore.FirebaseFirestore
import com.whiteside.cafe.model.Product

class ProductPresenter {
    private lateinit var listener: OnGetProductListener

    fun setListener(listener: OnGetProductListener) {
        this.listener = listener
    }

    private fun getProductFromFirebase(categoryName: String, productId: String) {
        val fStore = FirebaseFirestore.getInstance()
        fStore.collection("Categories")
            .document(categoryName)
            .collection("Products")
            .document(productId)
            .get()
            .addOnSuccessListener {
                val product = it.toObject(Product::class.java)!!
                product.productId = (it.id)
                listener.onGetProductSuccess(product)
            }
            .addOnFailureListener {
                listener.onGetProductFailed(it)
            }
    }

    fun getProductInfo(categoryName: String, productId: String) {
        getProductFromFirebase(categoryName, productId)
    }
}