package com.whiteside.cafe.api.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.whiteside.cafe.model.Product

interface ProductRepository {

    fun addProduct(product: Product): Task<DocumentSnapshot>
    fun getProduct(categoryName: String, productId: String): Task<DocumentSnapshot>
}