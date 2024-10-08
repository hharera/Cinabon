package com.harera.repository.impl

import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.harera.local.MarketDao
import com.harera.repository.CategoryRepository
import com.harera.repository.DBConstants
import com.harera.repository.domain.Product
import com.harera.repository.mapper.CategoryMapper
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import com.harera.repository.domain.Category as CategoryGet

class CategoryRepositoryImpl @Inject constructor(
    private val fStore: FirebaseFirestore,
    private val fStorage: FirebaseStorage,
    private val marketDao: MarketDao,
) : CategoryRepository {

    override fun getCategoryProducts(categoryName: String) =
        fStore
            .collection(DBConstants.PRODUCTS)
            .whereEqualTo(Product::categoryName.name, categoryName)
            .get()

    override fun addCategory(category: CategoryGet): Task<Void> =
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

    override suspend fun getCategories(fetchOnline: Boolean): Result<List<CategoryGet>> = kotlin.runCatching {
        if (fetchOnline) {
            return@runCatching fStore
                .collection(DBConstants.CATEGORIES)
                .get()
                .let {
                    Tasks.await(it).map { it.toObject(CategoryGet::class.java) }
                }
        } else {
            marketDao.getCategories().map {
                CategoryMapper.toCategory(it)
            }
        }
    }
}