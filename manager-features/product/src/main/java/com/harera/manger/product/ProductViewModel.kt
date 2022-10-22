package com.harera.manger.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.harera.common.base.BaseViewModel
import com.harera.common.utils.Response
import com.harera.repository.*
import com.harera.repository.domain.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val wishListRepository: WishListRepository,
    private val categoryRepository: CategoryRepository,
    private val authManager: UserRepository,
) : BaseViewModel() {

    private val _productId = MutableLiveData<String>()
    val productId: LiveData<String> = _productId

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState

    private val _wishState = MutableLiveData<Boolean>()
    val wishState: LiveData<Boolean> = _wishState

    private val _cartState = MutableLiveData<Boolean>()
    val cartState: LiveData<Boolean> = _cartState

    private val _products: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>> = _products

    fun getProduct() = liveData(Dispatchers.IO) {
        emit(Response.loading(data = null))
//
//        val task = productRepository.getProduct(_productId.value!!)
//        val result = Tasks.await(task)
//
//        emit(
//            if (task.isSuccessful)
//                Response.success(data = result.toObject(Product::class.java)!!)
//            else
//                Response.error(error = task.exception, data = null)
//        )
    }

    fun setProductId(productId: String) {
        _productId.value = productId
    }

    fun getCartState() = viewModelScope.launch(Dispatchers.IO) {
//        cartRepository
//            .checkCart(productId.value!!, authManager.getCurrentUser()!!.uid)
//            .onSuccess {
//                updateCartState(it)
//            }
//            .onFailure {
//                handleException(it)
//            }
    }

    private fun updateCartState(state: Boolean) = viewModelScope.launch(Dispatchers.Main) {
        _cartState.value = state
    }

    suspend fun checkWishState() {
//        wishListRepository
//            .checkWishItem(
//                _productId.value!!,
//                authManager.getCurrentUser()!!.uid
//            )
//            .onSuccess {
//                updateWishState(it)
//            }
//            .onFailure {
//                handleException(it)
//            }
    }

    private fun updateWishState(state: Boolean) {
        _wishState.postValue(state)
    }

    fun changeCartState() = viewModelScope.launch(Dispatchers.IO) {
        this@ProductViewModel.getCartState().join()

        if (cartState.value!!) {
            removeCartItem()
        } else {
            addCartItem()
        }
    }

    suspend fun changeWishState() {
//        wishListRepository
//            .checkWishItem(
//                _productId.value!!,
//                authManager.getCurrentUser()!!.uid
//            )
//            .onSuccess {
//                updateWishState(it)
//            }
//            .onFailure {
//                handleException(it)
//            }
//
//        if (wishState.value!!) {
//            removeWishItem()
//        } else {
//            addWishItem()
//        }
    }

    private fun removeWishItem() = viewModelScope.launch(Dispatchers.IO) {
//        wishListRepository.removeWishListItem(
//            _productId.value!!,
//            authManager.getCurrentUser()!!.uid
//        ).onFailure {
//            handleException(it)
//        }.onSuccess {
//            updateWishState(false)
//        }
    }

    private fun addWishItem() = viewModelScope.launch(Dispatchers.IO) {
//        val item = WishItem(
//            productId = _productId.value!!,
//            uid = authManager.getCurrentUser()!!.uid,
//            time = Timestamp.now()
//        )
//
//        wishListRepository
//            .addWishListItem(item)
//            .onSuccess {
//                updateWishState(true)
//            }
//            .onFailure {
//                handleException(it)
//            }
    }

    private fun addCartItem() = viewModelScope.launch(Dispatchers.IO) {
//        val item = CartItem(
//            productId = _productId.value!!,
//            uid = authManager.getCurrentUser()!!.uid,
//            quantity = 1,
//            time = Timestamp.now()
//        )
//
//        cartRepository
//            .addCartItem(item)
//            .onSuccess {
//                updateCartState(true)
//            }
//            .onFailure {
//                handleException(it)
//            }
    }

    private fun removeCartItem() = viewModelScope.launch(Dispatchers.IO) {
//        cartRepository
//            .removeCartItem(_productId.value!!, authManager.getCurrentUser()!!.uid)
//            .onSuccess {
//                updateCartState(!it)
//            }
//            .onFailure {
//                handleException(it)
//            }
    }

    fun getCategoryProducts(category: String) {
        categoryRepository
            .getCategoryProducts(category)
            .addOnSuccessListener {
                it.documents.map {
                    it.toObject(Product::class.java)!!
                }.let {
                    _products.value = it
                }
            }.addOnFailureListener {
                handleException(it)
            }
    }
}