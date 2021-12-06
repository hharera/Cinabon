package com.harera.repository.abstraction

import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.UploadTask
import com.harera.model.model.Product

interface ProductRepository {

    suspend fun addProduct(product: Product): Result<Boolean>
    suspend fun getProduct(productId: String): Result<Product>
    suspend fun uploadProductImage(productId: String, imageBitmap: Bitmap): Result<String>
    suspend fun getProducts(limit: Int): Result<List<Product>>
    suspend fun uploadProductImage(productId: String, productImageUrl: String): Result<Boolean>
}