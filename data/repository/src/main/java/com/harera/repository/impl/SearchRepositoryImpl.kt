package com.harera.repository.impl

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.harera.repository.abstraction.DBConstants.PRODUCTS
import com.harera.repository.abstraction.SearchRepository
import com.harera.repository.domain.Product
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val fStore: FirebaseFirestore
) : SearchRepository {

    override fun searchProducts(text: String): Task<QuerySnapshot> =
        fStore
            .collection(PRODUCTS)
            .whereGreaterThanOrEqualTo(Product::title.name, text)
            .get()
}
