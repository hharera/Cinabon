package com.whiteside.cafe.api.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.whiteside.cafe.api.repository.CategoryRepository
import javax.inject.Inject

class FirebaseCategoryRepository @Inject constructor() : CategoryRepository {
    val fStore by lazy { FirebaseFirestore.getInstance() }

    override fun getProducts(categoryName: String) =
        fStore.collection("Categories")
            .document(categoryName)
            .collection("Products")
            .get()
}