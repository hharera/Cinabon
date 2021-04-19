package com.whiteside.cafe.ui.product

import com.whiteside.cafe.common.firebase.FirebaseProductRepository
import com.whiteside.cafe.model.Product
import javax.inject.Inject

class ProductPresenter @Inject constructor(val productRepository: FirebaseProductRepository) {

    fun getProduct(categoryName: String, productId: String, result: (Product) -> Unit) {
        productRepository.getProduct(categoryName, productId)
            .addOnSuccessListener {
                val product = it.toObject(Product::class.java)!!
                result(product)
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }
}