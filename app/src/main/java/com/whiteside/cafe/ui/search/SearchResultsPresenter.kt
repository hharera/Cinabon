package com.whiteside.cafe.ui.search

import com.whiteside.cafe.api.repository.SearchRepository
import com.whiteside.cafe.model.Product
import com.whiteside.cafe.utils.Categories
import javax.inject.Inject

class SearchResultsPresenter @Inject constructor(val repo: SearchRepository) {

    fun searchProduct(productName: String, response: SearchListener<Product>) {
        response.onLoading()
        Categories.values().forEach {

            repo.searchProduct(productName, it.name)
                .addOnSuccessListener {
                    it.documents.forEach {
                        val product = it.toObject(Product::class.java)!!
                        response.onSuccess(product)
                    }
                    if (it.documents.isEmpty()) {
                        response.onEmptySearch()
                    }
                }.addOnFailureListener {
                    response.onFailed(it)
                }
        }
    }
}