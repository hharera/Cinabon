package com.harera.repository.repository

import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.UploadTask
import com.harera.model.modelset.Category

interface CategoryRepository {

    fun getCategoryProducts(categoryName: String): Task<QuerySnapshot>
    fun getCategories(): Task<QuerySnapshot>
    fun addCategory(category: Category): Task<Void>
    fun uploadCategoryImage(categoryName: String, imageBitmap: Bitmap): UploadTask
}