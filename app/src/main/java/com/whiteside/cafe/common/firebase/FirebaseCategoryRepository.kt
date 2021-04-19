package com.whiteside.cafe.common.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.whiteside.cafe.common.repository.CategoryRepository
import javax.inject.Inject

class FirebaseCategoryRepository @Inject constructor() : CategoryRepository {
    val fStore by lazy { FirebaseFirestore.getInstance() }

    override fun getProducts(categoryName: String) =
        fStore.collection("Categories")
            .document(categoryName)
            .collection("Products")
            .get()
}