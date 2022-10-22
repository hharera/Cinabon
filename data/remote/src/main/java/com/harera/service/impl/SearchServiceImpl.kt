package com.harera.service.impl

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.harera.service.DBConstants.PRODUCTS
import com.harera.service.SearchService
import com.harera.service.domain.ServiceDomainProduct
import javax.inject.Inject

class SearchServiceImpl @Inject constructor(
    private val fStore: FirebaseFirestore
) : SearchService {

    override fun searchProducts(text: String): Task<QuerySnapshot> =
        fStore
            .collection(PRODUCTS)
            .whereGreaterThanOrEqualTo(ServiceDomainProduct::title.name, text)
            .get()
}
