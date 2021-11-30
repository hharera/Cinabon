package com.harera.repository.impl

import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.harera.model.modelset.Product
import com.harera.repository.abstraction.DBConstants.PRODUCTS
import com.harera.repository.abstraction.ProductRepository
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class FirebaseProductRepository @Inject constructor(
    private val fStore: FirebaseFirestore,
    private val fStorage: FirebaseStorage,
) : ProductRepository {

    override fun addProduct(product: Product) =
        fStore
            .collection(PRODUCTS)
            .document()
            .apply {
                product.productId = id
            }
            .set(product)

    override fun getProduct(productId: String) =
        fStore
            .collection(PRODUCTS)
            .document(productId)
            .get()

    override fun uploadProductImage(productId: String, imageBitmap: Bitmap): UploadTask {
        val inputStream = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, inputStream)

        return fStorage
            .reference
            .child(PRODUCTS)
            .child(productId)
            .putBytes(inputStream.toByteArray())
    }

    override fun getProducts(limit: Int): Task<QuerySnapshot> =
        fStore
            .collection(PRODUCTS)
            .orderBy(Product::title.name)
            .limitToLast(limit.toLong())
            .get()

    override fun updateProductImage(productId: String, productImageUrl: String): Task<Void> =
        fStore
            .collection(PRODUCTS)
            .document(productId)
            .update(Product::productPictureUrls.name, arrayListOf(productImageUrl))
}