package com.whiteside.cafe.api.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.whiteside.cafe.api.repository.SearchRepository
import javax.inject.Inject

class FirebaseSearchRepository @Inject constructor() : SearchRepository {
    val fStore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    override fun searchProduct(productName: String, categoryName: String): Task<QuerySnapshot> =
            fStore.collection("Categories")
                .document(categoryName)
                .collection("Products")
                .whereLessThanOrEqualTo("title", productName)
                .get()

}
