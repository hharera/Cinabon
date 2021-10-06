package com.harera.repository.repository

import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.UploadTask
import com.harera.model.modelset.Product

interface ProductRepository {

    fun addProduct(product: Product): Task<Void>
    fun getProduct(productId: String): Task<DocumentSnapshot>
    fun uploadProductImage(productId: String, imageBitmap: Bitmap): UploadTask
    fun getProducts(limit: Int): Task<QuerySnapshot>
    fun updateProductImage(productId: String, productImageUrl: String): Task<Void>
}