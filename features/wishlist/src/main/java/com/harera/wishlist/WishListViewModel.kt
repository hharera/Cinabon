package com.harera.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Tasks
import com.harera.common.base.BaseViewModel
import com.harera.repository.CartRepository
import com.harera.repository.ProductRepository
import com.harera.repository.UserRepository
import com.harera.repository.WishListRepository
import com.harera.repository.domain.WishItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishListViewModel @Inject constructor(
    private val wishListRepository: WishListRepository,
    private val cartRepository: CartRepository,
    private val authManager: UserRepository,
    private val productRepository: ProductRepository,
) : BaseViewModel() {

    private val _productId = MutableLiveData<String>()

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
        updateLoading(false)
    }

    private fun getWishListItemsDetails(list: List<WishItem>) {
//        viewModelScope.launch(Dispatchers.IO) {
//            list.map { wishItem ->
//                val product = async(Dispatchers.IO) {
//                    Tasks.await(
//                        productRepository.getProduct(wishItem.productId)
//                    ).toObject(Product::class.java)!!
//                }.await()
//                wishItem.productTitle = product.title
//                wishItem.productImageUrl = product.productPictureUrls.first()
//                wishItem
//            }.let {
//                updateWishList(it)
//            }

    }

    fun addWishItemToCart(productId: String) = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)
        removeWishItem(productId = productId)
        addCartItem(productId).await()
        updateLoading(false)
    }

    private fun addCartItem(productId: String) = viewModelScope.async(Dispatchers.IO) {
//        cartRepository.addCartItem(
//            CartItem(
//                uid = authManager.getCurrentUser()!!.uid,
//                productId = productId,
//                time = Timestamp.now(),
//                quantity = 1
//            )
//        )
    }

    fun removeWishItem(productId: String) = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)
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
            updateLoading(false)
        }
    }

    private fun updateWishList(list: List<WishItem>) = viewModelScope.launch(Dispatchers.Main) {
        _wishList.value = list.associateBy { it.productId }
    }
}