package com.harera.features.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Timestamp
import com.harera.common.base.BaseViewModel
import com.harera.repository.domain.CartItem
import com.harera.repository.abstraction.UserRepository
import com.harera.repository.abstraction.CartRepository
import com.harera.repository.abstraction.ProductRepository
import com.harera.repository.abstraction.WishListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val wishListRepository: WishListRepository,
    private val authManager: UserRepository,
    private val productRepository: ProductRepository,
) : BaseViewModel() {

    private val _cartList = MutableLiveData<Map<String, CartItem>>()
    val cartList: LiveData<Map<String, CartItem>> = _cartList

    fun removeItem(cartItemId: String) {
        updateLoading(true)
        cartRepository.removeCartItem(
            cartItemId
        ).addOnSuccessListener {
            updateLoading(false)
            _cartList.value!!.minus(cartItemId).let {
                _cartList.value = it
            }
        }.addOnFailureListener {
            updateLoading(false)
            updateException(it)
        }
    }

    fun getCartItems() = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)

        val task = cartRepository.getCartItems(authManager.getCurrentUser()!!.uid)
        val result = Tasks.await(task)

        if (task.isSuccessful)
            getCartItemsDetails(result.documents.map { it.toObject(CartItem::class.java)!! })
        else
            updateException(task.exception)

        updateLoading(false)
    }

    private fun getCartItemsDetails(list: List<CartItem>) = viewModelScope.launch(Dispatchers.IO) {
        list.map { cartItem ->
            productRepository.getProduct(cartItem.productId).asLiveData().value
            cartItem.productTitle = product.title
            cartItem.productPrice = product.price.toFloat()
            cartItem.productImageUrl = product.productPictureUrls.first()
            cartItem
        }.let {
            updateCartList(it)
        }
    }

    fun updateQuantity(cartItemId: String, quantity: Int) = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)

        val task = cartRepository.updateQuantity(
            cartItemId,
            quantity
        )
        Tasks.await(task)

        if (task.isSuccessful) {
            _cartList.value!![cartItemId]!!.quantity = quantity
            updateCartList(_cartList.value!!.map { it.value })
        } else
            updateException(task.exception)
        updateLoading(false)
    }

    private fun updateCartList(list: List<CartItem>) = viewModelScope.launch(Dispatchers.Main) {
        _cartList.value = list.associateBy { it.productId + it.uid }
    }

    private suspend fun addItemToFavourite(cartItemId: String) =
        viewModelScope.async(Dispatchers.IO) {
            updateLoading(true)

            val cartItem = cartList.value!![cartItemId]!!
            val task = wishListRepository.addWishListItem(
                WishListItem(
                    cartItem.uid,
                    cartItem.productId,
                    Timestamp.now()
                )
            )
            Tasks.await(task)

            if (task.isSuccessful) {
                _cartList.value!!.minus(cartItemId).let {
                    updateCartList(it.map { it.value })
                }
            } else {
                updateException(task.exception)
            }

            updateLoading(false)
            return@async task.isSuccessful
        }.await()

    fun moveToFavourite(cartItemId: String) {
        viewModelScope.launch {
            updateLoading(true)
            val result = addItemToFavourite(cartItemId)
            if (result)
                removeItem(cartItemId)
            updateLoading(false)
        }
    }

    fun plusQuantity(cartItemId: String) {
        cartRepository.updateQuantity(
            cartItemId,
            cartList.value!![cartItemId]!!.quantity
        )
    }

    fun minusQuantity(cartItemId: String) {
        cartRepository.updateQuantity(
            cartItemId,
            cartList.value!![cartItemId]!!.quantity
        )
    }
}