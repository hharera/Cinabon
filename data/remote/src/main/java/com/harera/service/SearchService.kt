package com.harera.service

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

interface SearchService {

    fun searchProducts(text: String): Task<QuerySnapshot>
}