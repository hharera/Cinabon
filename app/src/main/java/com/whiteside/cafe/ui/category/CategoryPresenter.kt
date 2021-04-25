package com.whiteside.cafe.ui.category

import com.whiteside.cafe.api.repository.CategoryRepository
import com.whiteside.cafe.model.Product
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryPresenter @Inject constructor(val repo: CategoryRepository) {

    fun getCategoryProducts(categoryName: String, result: (Product) -> Unit) {
            repo.getProducts(categoryName)
                .addOnSuccessListener {
                    for (ds in it.documents) {
                        GlobalScope.launch {
                            val product = async { ds.toObject(Product::class.java)!! }
                            result(product.await())
                        }
                    }
        }
    }
}