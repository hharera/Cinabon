package com.whiteside.cafe.ui.category

import com.whiteside.cafe.api.firebase.FirebaseCategoryRepository
import com.whiteside.cafe.model.Product
import javax.inject.Inject

class CategoryPresenter @Inject constructor(val repo: FirebaseCategoryRepository) {

    fun getCategoryProducts(categoryName: String, result: (Product) -> Unit) {
        repo.getProducts(categoryName)
            .addOnSuccessListener {
                for (ds in it.documents) {
                    val product = ds.toObject(Product::class.java)!!
                    result(product)
                }
            }
    }
}