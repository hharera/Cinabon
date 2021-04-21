package com.whiteside.cafe.ui.product

import com.whiteside.cafe.api.repository.ProductRepository
import com.whiteside.cafe.model.Product
import javax.inject.Inject

class ProductPresenter @Inject constructor(val repo: ProductRepository) {

    fun getProduct(categoryName: String, productId: String, result: (Product) -> Unit) {
        repo.getProduct(categoryName, productId)
            .addOnSuccessListener {
                val product = it.toObject(Product::class.java)!!
                result(product)
            }
    }
}