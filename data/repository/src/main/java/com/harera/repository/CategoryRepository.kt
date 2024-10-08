package com.harera.repository

import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.UploadTask
import com.harera.repository.domain.Category

interface CategoryRepository {

    fun getCategoryProducts(categoryName: String): Task<QuerySnapshot>
    suspend fun getCategories(fetchOnline: Boolean): Result<List<Category>>
    fun addCategory(category: Category): Task<Void>
    fun uploadCategoryImage(categoryName: String, imageBitmap: Bitmap): UploadTask
}