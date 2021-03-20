package com.whiteside.cafe.ui.category

import com.google.firebase.firestore.FirebaseFirestore
import com.whiteside.cafe.model.Product
import com.whiteside.cafe.ui.product.OnGetProductListener

class CategoryProductsPresenter(var listener: OnGetProductListener?) {
    private fun getProductsFromFirebase(categoryName: String?) {
        val fStore = FirebaseFirestore.getInstance()
        fStore.collection("Categories")
            .document(categoryName)
            .collection("Products")
            .get()
            .addOnSuccessListener { v ->
                for (ds in v.documents) {
                    val product = ds.toObject(Product::class.java)
                    product.setProductId(ds.id)
                    listener.onGetProductSuccess(product)
                }
            }
    }

    fun getProducts(categoryName: String?) {
        getProductsFromFirebase(categoryName)
    }
}