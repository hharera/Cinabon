package com.harera.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Tasks
import com.harera.model.modelget.Category
import com.harera.model.modelget.Offer
import com.harera.model.modelget.Product
import com.harera.repository.repository.CategoryRepository
import com.harera.repository.repository.OfferRepository
import com.harera.repository.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val offerRepository: OfferRepository,
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
) : ViewModel() {

    private val _products: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>> = _products

    private val _categories: MutableLiveData<List<Category>> = MutableLiveData()
    val categories: LiveData<List<Category>> = _categories

    private val _offers: MutableLiveData<List<Offer>> = MutableLiveData()
    val offers: LiveData<List<Offer>> = _offers

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _exception: MutableLiveData<Exception> = MutableLiveData()
    val exception: LiveData<Exception> = _exception

    fun getCategories() {
        updateLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            val task = categoryRepository.getCategories()
            val result = Tasks.await(task)

            updateLoading(false)

            if (task.isSuccessful) {
                result.documents.map {
                    it.toObject(Category::class.java)!!
                }.let {
                    updateCategories(it)
                }
            } else {
                updateException(task.exception)
            }
        }
    }

    fun getProducts() {
        updateLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            val task = productRepository.getProducts(20)
            val result = Tasks.await(task)

            updateLoading(false)

            if (task.isSuccessful) {
                result.documents.map {
                    it.toObject(Product::class.java)!!
                }.let {
                    updateProducts(it)
                }
            } else {
                updateException(task.exception)
            }
        }
    }

    fun getOffers() {
        updateLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            val task = offerRepository.getOffers()
            val result = Tasks.await(task)

            updateLoading(false)

            if (task.isSuccessful) {
                result.documents.map {
                    it.toObject(Offer::class.java)!!
                }.let {
                    updateOffers(it)
                }
            } else {
                updateException(task.exception)
            }
        }
    }

    private fun updateProducts(products: List<Product>) {
        viewModelScope.launch(Dispatchers.Main) {
            _products.value = products
        }
    }

    private fun updateLoading(state: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            _loading.value = state
        }
    }

    private fun updateException(exception: Exception?) {
        viewModelScope.launch(Dispatchers.Main) {
            exception?.let {
                _exception.value = it
            }
        }
    }

    private fun updateCategories(categories: List<Category>) {
        viewModelScope.launch(Dispatchers.Main) {
            _categories.value = categories
        }
    }

    private fun updateOffers(offers: List<Offer>) {
        viewModelScope.launch(Dispatchers.Main) {
            _offers.value = offers
        }
    }
}
