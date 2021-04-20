package com.whiteside.cafe.api.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.whiteside.cafe.api.repository.ProductRepository
import com.whiteside.cafe.model.Product
import javax.inject.Inject

class FirebaseProductRepository @Inject constructor() : ProductRepository {
    val fStore by lazy { FirebaseFirestore.getInstance() }

    override fun addProduct(product: Product) =
        fStore.collection("Categories")
            .document(product.categoryName)
            .collection("Products")
            .document(product.productId)
            .get()

    override fun getProduct(categoryName: String, productId: String) =
        fStore.collection("Categories")
            .document(categoryName)
            .collection("Products")
            .document(productId)
            .get()
}