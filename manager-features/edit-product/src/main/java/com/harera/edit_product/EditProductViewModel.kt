package com.harera.edit_product

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Tasks
import com.harera.model.modelget.Product
import com.harera.repository.abstraction.AuthManager
import com.harera.repository.abstraction.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProductViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _productId: MutableLiveData<String> = MutableLiveData()
    val productId: LiveData<String> = _productId

    private val _operationCompleted: MutableLiveData<Boolean> = MutableLiveData()
    val operationCompleted: LiveData<Boolean> = _operationCompleted

    private val _image: MutableLiveData<Bitmap> = MutableLiveData()
    val image: LiveData<Bitmap> = _image

    private val _imageUrl: MutableLiveData<String> = MutableLiveData()
    val imageUrl: LiveData<String> = _imageUrl

    private val _product: MutableLiveData<Product> =
        MutableLiveData()
    val product: LiveData<Product> = _product

    fun setImage(imageBitmap: Bitmap) {
        _image.value = imageBitmap
    }

    private fun uploadProductImage() = viewModelScope.async(Dispatchers.IO) {
        val task = productRepository.uploadProductImage(
            product.value!!.productId,
            image.value!!,
        )
        val result = Tasks.await(task)
        if (task.isSuccessful)
            result.storage.downloadUrl.let {
                return@let Tasks.await(it).toString()
            }.let {
                setImageUrl(it)
            }

        return@async task.isSuccessful
    }

    private fun setImageUrl(imageUrl: String) = viewModelScope.launch(Dispatchers.Main) {
        _imageUrl.value = imageUrl
    }

    fun updateProduct() {
        if (_image.value == null) {
            _operationCompleted.value = true
            return
        }
        viewModelScope.launch {
            if (uploadProductImage().await()) {
                updateProductImage()
            }
        }
    }

    private fun updateProductImage() {
        productRepository.updateProductImage(
            productId = product.value!!.productId,
            productImageUrl = imageUrl.value!!,
        ).addOnSuccessListener {
            _operationCompleted.value = true
        }
    }

    fun getProduct() {
        productRepository.getProduct(productId = productId.value!!).addOnSuccessListener {
            it.toObject(Product::class.java)!!.let {
                _product.value = it
            }
        }
    }

    fun setProductId(it: String) {
        _productId.value = it
    }
}