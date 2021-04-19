package com.whiteside.cafe.common.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.whiteside.cafe.common.repository.ProductRepository
import com.whiteside.cafe.model.Product
import javax.inject.Inject

class FirebaseProductRepository @Inject constructor() : ProductRepository {
    val fStore by lazy { FirebaseFirestore.getInstance() }

    override fun addProduct(product: Product) {
    }

    override fun getProduct(categoryName: String, productId: String) =
        fStore.collection("Categories")
            .document(categoryName)
            .collection("Products")
            .document(productId)
            .get()
}