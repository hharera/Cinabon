package com.whiteside.cafe.api.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

interface SearchRepository {

    fun searchProduct(productName: String, categoryName: String): Task<QuerySnapshot>
}