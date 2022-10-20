package com.harera.service.abstraction

import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.UploadTask
import com.harera.service.domain.ServiceDomainCategory

interface CategoryService {

    fun getCategoryProducts(categoryName: String): Task<QuerySnapshot>
    suspend fun getCategories(fetchOnline: Boolean): List<ServiceDomainCategory>
    fun addCategory(serviceDomainCategory: ServiceDomainCategory): Task<Void>
    fun uploadCategoryImage(categoryName: String, imageBitmap: Bitmap): UploadTask
}