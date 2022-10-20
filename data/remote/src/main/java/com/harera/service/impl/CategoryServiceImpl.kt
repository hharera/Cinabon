package com.harera.service.impl

import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.harera.service.abstraction.CategoryService
import com.harera.service.abstraction.DBConstants
import com.harera.service.domain.ServiceDomainCategory
import com.harera.service.domain.ServiceDomainProduct
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class CategoryServiceImpl @Inject constructor(
    private val fStore: FirebaseFirestore,
    private val fStorage: FirebaseStorage,
) : CategoryService {

    override fun getCategoryProducts(categoryName: String) =
        fStore
            .collection(DBConstants.PRODUCTS)
            .whereEqualTo(ServiceDomainProduct::categoryName.name, categoryName)
            .get()

    override fun addCategory(serviceDomainCategory: ServiceDomainCategory): Task<Void> =
        fStore
            .collection(DBConstants.CATEGORIES)
            .document(serviceDomainCategory.categoryName)
            .set(serviceDomainCategory)

    override fun uploadCategoryImage(categoryName: String, imageBitmap: Bitmap): UploadTask {
        val inputStream = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, inputStream)

        return fStorage
            .reference
            .child(DBConstants.CATEGORIES)
            .child(categoryName)
            .putBytes(inputStream.toByteArray())
    }

    override suspend fun getCategories(fetchOnline: Boolean): List<ServiceDomainCategory> =
        fStore
            .collection(DBConstants.CATEGORIES)
            .get()
            .let {
                Tasks.await(it).map { it.toObject(ServiceDomainCategory::class.java) }
            }
}