package com.harera.repository.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.harera.repository.DBConstants.PRODUCTS
import com.harera.model.modelset.Product
import com.harera.repository.repository.SearchRepository
import javax.inject.Inject

class FirebaseSearchRepository @Inject constructor(
    private val fStore: FirebaseFirestore
) : SearchRepository {

    override fun searchProducts(text: String): Task<QuerySnapshot> =
        fStore
            .collection(PRODUCTS)
            .whereGreaterThanOrEqualTo(Product::title.name, text)
            .get()
}
