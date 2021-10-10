package com.harera.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Timestamp
import com.harera.model.modelget.Product
import com.harera.model.modelget.WishItem
import com.harera.model.modelset.CartItem
import com.harera.repository.repository.AuthManager
import com.harera.repository.repository.CartRepository
import com.harera.repository.repository.ProductRepository
import com.harera.repository.repository.WishListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishListViewModel @Inject constructor(
    private val wishListRepository: WishListRepository,
    private val cartRepository: CartRepository,
    private val authManager: AuthManager,
    private val productRepository: ProductRepository,
) : ViewModel() {

    private val _productId = MutableLiveData<String>()

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState

    private val _exception = MutableLiveData<Exception?>()
    val exception: LiveData<Exception?> = _exception

    private val _wishList = MutableLiveData<Map<String, WishItem>>()
    val wishList: LiveData<Map<String, WishItem>> = _wishList

    fun getWishListItems() = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)

        val task = wishListRepository.getWishListItems(authManager.getCurrentUser()!!.uid)
        val result = Tasks.await(task)

        if (task.isSuccessful)
            getWishListItemsDetails(result.documents.map { it.toObject(WishItem::class.java)!! })
        else
            updateException(task.exception)
    }

    private fun getWishListItemsDetails(list: List<WishItem>) =
        viewModelScope.launch(Dispatchers.IO) {
            list.map { wishItem ->
                val product = async(Dispatchers.IO) {
                    Tasks.await(
                        productRepository.getProduct(wishItem.productId)
                    ).toObject(Product::class.java)!!
                }.await()
                wishItem.productTitle = product.title
                wishItem.productImageUrl = product.productPictureUrls[0]
                wishItem
            }.let {
                updateWishList(it)
            }
        }

    fun addWishItemToCart(productId: String) = viewModelScope.launch(Dispatchers.IO) {
        removeWishItem(productId = productId)
        addCartItem(productId).await()
    }

    private fun addCartItem(productId: String) = viewModelScope.async(Dispatchers.IO) {
        cartRepository.addCartItem(
            CartItem(
                uid = authManager.getCurrentUser()!!.uid,
                productId = productId,
                time = Timestamp.now(),
                quantity = 1
            )
        )
    }

    fun removeWishItem(productId: String) = viewModelScope.launch(Dispatchers.IO) {
        viewModelScope.launch(Dispatchers.IO) {
            updateLoading(true)

            val task =
                wishListRepository.removeWishListItem(productId, authManager.getCurrentUser()!!.uid)
            Tasks.await(task)

            if (task.isSuccessful)
                _wishList.value!!.minus(productId).let {
                    updateWishList(it.map { it.value })
                }
            else
                updateException(task.exception)
        }
    }

    private fun updateLoading(state: Boolean) = viewModelScope.launch(Dispatchers.Main) {
        _loadingState.value = state
    }

    private fun updateException(exception: Exception?) = viewModelScope.launch(Dispatchers.Main) {
        _exception.value = exception
    }

    private fun updateWishList(list: List<WishItem>) = viewModelScope.launch(Dispatchers.Main) {
        _wishList.value = list.associateBy { it.productId }
    }
}