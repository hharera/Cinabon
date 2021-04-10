package com.whiteside.cafe.ui.category

import com.google.firebase.firestore.FirebaseFirestore
import com.whiteside.cafe.model.Product
import com.whiteside.cafe.ui.product.OnGetProductListener

class CategoryProductsPresenter(var listener: OnGetProductListener) {

    private fun getProductsFromFirebase(categoryName: String) {
        val fStore = FirebaseFirestore.getInstance()

        fStore.collection("Categories")
            .document(categoryName)
            .collection("Products")
            //TODO add mechanism to load more when end
            .limit(10)
            .get()
            .addOnSuccessListener {
                for (ds in it.documents) {
                    val product = ds.toObject(Product::class.java)!!
                    listener.onGetProductSuccess(product)
                }
            }
    }

    fun getProducts(categoryName: String) {
        getProductsFromFirebase(categoryName)
    }
}