package com.harera.repository.impl

import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.harera.model.modelset.Category
import com.harera.model.modelset.Product
import com.harera.repository.abstraction.CategoryRepository
import com.harera.repository.abstraction.DBConstants
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import com.harera.model.modelget.Category as CategoryGet

class FirebaseCategoryRepository @Inject constructor(
    private val fStore: FirebaseFirestore,
    private val fStorage: FirebaseStorage,
) : CategoryRepository {

    override fun getCategoryProducts(categoryName: String) =
        fStore
            .collection(DBConstants.PRODUCTS)
            .whereEqualTo(Product::categoryName.name, categoryName)
            .get()

    override fun addCategory(category: Category): Task<Void> =
        fStore
            .collection(DBConstants.CATEGORIES)
            .document(category.categoryName)
            .set(category)

    override fun uploadCategoryImage(categoryName: String, imageBitmap: Bitmap): UploadTask {
        val inputStream = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, inputStream)

        return fStorage
            .reference
            .child(DBConstants.CATEGORIES)
            .child(categoryName)
            .putBytes(inputStream.toByteArray())
    }

    override suspend fun getCategories(fetchOnline: Boolean): List<CategoryGet> =
        fStore
            .collection(DBConstants.CATEGORIES)
            .get()
            .let {
                Tasks.await(it).map { it.toObject(CategoryGet::class.java) }
            }
}