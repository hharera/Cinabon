package com.whiteside.cafe.Category

import Model.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.whiteside.cafe.Product.OnGetProductListener

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