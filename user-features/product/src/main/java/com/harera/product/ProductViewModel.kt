package com.harera.product

import androidx.lifecycle.*
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Timestamp
import com.harera.common.utils.Response
import com.harera.model.modelget.Product
import com.harera.model.modelset.CartItem
import com.harera.model.modelset.WishListItem
import com.harera.repository.abstraction.*
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
    private val authManager: AuthManager,
) : ViewModel() {

    private val _productId = MutableLiveData<String>()

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState

    private val _exception = MutableLiveData<Exception?>()
    val exception: LiveData<Exception?> = _exception

    private val _wishState = MutableLiveData<Boolean>()
    val wishState: LiveData<Boolean> = _wishState

    private val _cartState = MutableLiveData<Boolean>()
    val cartState: LiveData<Boolean> = _cartState

    private val _products: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>> = _products

    fun getProduct() = liveData(Dispatchers.IO) {
        emit(Response.loading(data = null))

        val task = productRepository.getProduct(_productId.value!!)
        val result = Tasks.await(task)

        emit(
            if (task.isSuccessful)
                Response.success(data = result.toObject(Product::class.java)!!)
            else
                Response.error(error = task.exception, data = null)
        )
    }

    fun setProductId(productId: String) {
        _productId.value = productId
    }

    fun getCartState() = viewModelScope.launch(Dispatchers.IO) {
        val task = cartRepository
            .getCartItem(_productId.value!!, authManager.getCurrentUser()!!.uid)
        val result = Tasks.await(task)

        if (task.isSuccessful) {
            updateCartState(result.documents.isNotEmpty())
        } else {
            updateException(task.exception)
        }
    }

    private fun updateException(exception: Exception?) = viewModelScope.launch(Dispatchers.Main) {
        _exception.value = exception
    }

    private fun updateCartState(state: Boolean) = viewModelScope.launch(Dispatchers.Main) {
        _cartState.value = state
    }

    fun getWishState() = viewModelScope.launch(Dispatchers.IO) {
        val task = wishListRepository
            .getWishItem(
                _productId.value!!,
                authManager.getCurrentUser()!!.uid
            )
        val result = Tasks.await(task)

        if (task.isSuccessful) {
            updateWishState(result.documents.isNotEmpty())
        } else {
            _exception.value = (task.exception)
        }
    }

    private fun updateWishState(state: Boolean) = viewModelScope.launch(Dispatchers.Main) {
        _wishState.value = state
    }

    fun changeCartState() = viewModelScope.launch(Dispatchers.IO) {
        this@ProductViewModel.getCartState().join()

        if (cartState.value!!) {
            removeCartItem()
        } else {
            addCartItem()
        }
    }

    fun changeWishState() = viewModelScope.launch(Dispatchers.IO) {
        this@ProductViewModel.getWishState().join()

        if (wishState.value!!) {
            removeWishItem()
        } else {
            addWishItem()
        }
    }

    private fun removeWishItem() = viewModelScope.launch(Dispatchers.IO) {
        val task = wishListRepository.removeWishListItem(
            _productId.value!!,
            authManager.getCurrentUser()!!.uid
        )
        Tasks.await(task)

        if (task.isSuccessful) {
            updateWishState(false)
        } else {
            updateException(task.exception)
        }
    }

    private fun addWishItem() = viewModelScope.launch(Dispatchers.IO) {
        val item = WishListItem(
            productId = _productId.value!!,
            uid = authManager.getCurrentUser()!!.uid,
            time = Timestamp.now()
        )

        val task = wishListRepository.addWishListItem(item)
        Tasks.await(task)

        if (task.isSuccessful) {
            updateWishState(true)
        } else {
            updateException(task.exception)
        }
    }

    private fun addCartItem() = viewModelScope.launch(Dispatchers.IO) {
        val item = CartItem(
            productId = _productId.value!!,
            uid = authManager.getCurrentUser()!!.uid,
            quantity = 1,
            time = Timestamp.now()
        )

        val task = cartRepository.addCartItem(item)
        Tasks.await(task)

        if (task.isSuccessful) {
            updateCartState(true)
        } else {
            updateException(task.exception)
        }
    }

    private fun removeCartItem() = viewModelScope.launch(Dispatchers.IO) {
        val task = cartRepository.removeCartItem(
            _productId.value!! + authManager.getCurrentUser()!!.uid
        )
        Tasks.await(task)

        if (task.isSuccessful) {
            updateCartState(false)
        } else {
            updateException(task.exception)
        }
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
                updateException(it)
            }
    }
}