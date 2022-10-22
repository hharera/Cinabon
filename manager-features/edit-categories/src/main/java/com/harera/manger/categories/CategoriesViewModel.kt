package com.harera.manger.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.harera.common.base.BaseViewModel
import com.harera.common.utils.Response
import com.harera.repository.CategoryRepository
import com.harera.repository.ProductRepository
import com.harera.repository.domain.Category
import com.harera.repository.domain.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository,
) : BaseViewModel() {

    private val _categories: MutableLiveData<List<Category>> = MutableLiveData()
    val categories: LiveData<List<Category>> = _categories

    private val _products: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>> = _products

    private val _categoryName = MutableLiveData<String?>(null)
    val categoryName: LiveData<String?> = _categoryName

    private val _isConnected = MutableLiveData<Boolean>(false)
    val isConnected: LiveData<Boolean> = _isConnected

    fun getCategories() = viewModelScope.launch {
        updateLoading(true)
        categoryRepository.getCategories(isConnected.value!!)
            .onSuccess {
                updateLoading(false)
                updateCategories(it)
            }
            .onFailure {
                updateLoading(false)
                updateException(it)
            }
    }

    fun addCategory(category: Category) = liveData {
        categoryRepository
            .addCategory(category)
            .addOnSuccessListener {
                viewModelScope.launch(Dispatchers.Main) {
                    emit(Response.success(data = null))
                }
            }.addOnFailureListener {
                it.printStackTrace()
            }
    }

    private fun updateProducts(products: List<Product>) {
        viewModelScope.launch(Dispatchers.Main) {
            _products.value = products
        }
    }

    private fun updateCategories(categories: List<Category>) {
        viewModelScope.launch(Dispatchers.Main) {
            _categories.value = categories
        }
    }

    fun setCategoryName(category: String) {
        _categoryName.value = category
    }

    fun getProducts() {
        if (_categoryName.value != null)
            getCategoryProducts(_categoryName.value!!)
        else
            getAllProducts()
    }

    private fun getCategoryProducts(category: String) {
        categoryRepository
            .getCategoryProducts(category)
            .addOnSuccessListener {
                it.documents.map {
                    it.toObject(Product::class.java)!!
                }.let {
                    _products.value = it
                }
            }.addOnFailureListener {
                updateException(it)
            }
    }

    private fun getAllProducts() {
//        updateLoading(true)
//        viewModelScope.launch(Dispatchers.IO) {
//            val task = productRepository.getProducts(20)
//            val result = Tasks.await(task)
//
//            updateLoading(false)
//
//            if (task.isSuccessful) {
//                result.documents.map {
//                    it.toObject(Product::class.java)!!
//                }.let {
//                    updateProducts(it)
//                    cacheProducts(it)
//                }
//            } else {
//                updateException(task.exception)
//            }
//        }
    }

    private fun cacheProducts(list: List<Product>) = viewModelScope.launch {
        kotlin.runCatching {
            list.forEach {
                productRepository.insertProduct(it, true)
            }
        }
    }

    fun setConnectionState(state: Boolean) {
        _isConnected.postValue(state)
    }

}