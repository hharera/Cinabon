package com.harera.offer

import androidx.lifecycle.*
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Timestamp
import com.harera.common.base.BaseViewModel
import com.harera.model.modelget.Offer
import com.harera.model.modelget.Product
import com.harera.model.modelset.CartItem
import com.harera.model.modelset.WishListItem
import com.harera.repository.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfferViewModel @Inject constructor(
    private val offerRepository: OfferRepository,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val wishListRepository: WishListRepository,
    private val authManager: AuthManager,
) : BaseViewModel() {

    private val _offerId = MutableLiveData<String>()

    private val _wishState = MutableLiveData<Boolean>()
    val wishState: LiveData<Boolean> = _wishState

    private val _cartState = MutableLiveData<Boolean>()
    val cartState: LiveData<Boolean> = _cartState

    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> = _product

    private val _offer: MutableLiveData<Offer> = MutableLiveData()
    val offer: LiveData<Offer> = _offer

    private val _offers: MutableLiveData<List<Offer>> = MutableLiveData()
    val offers: LiveData<List<Offer>> = _offers

    fun getOffer() {
        offerRepository
            .getOfferById(_offerId.value!!)
            .addOnSuccessListener {
                it.toObject(Offer::class.java)!!.let {
                    _offer.value = it
                }
            }.addOnFailureListener {
                getProduct()
            }
    }

    private fun getProduct() {
        productRepository
            .getProduct(offer.value!!.productId)
            .addOnSuccessListener {
                it.toObject(Product::class.java)!!.let {
                    _product.value = it
                }
            }
            .addOnFailureListener {
                updateException(it)
            }
    }

    fun setOfferId(productId: String) {
        _offerId.value = productId
    }

    fun getCartState() = viewModelScope.launch(Dispatchers.IO) {
        val task = cartRepository
            .getCartItem(
                _offerId.value!! + authManager.getCurrentUser()!!.uid
            )
        val result = Tasks.await(task)

        if (task.isSuccessful) {
            updateCartState(result.documents.isNotEmpty())
        } else {
            updateException(task.exception)
        }
    }

    private fun updateCartState(state: Boolean) = viewModelScope.launch(Dispatchers.Main) {
        _cartState.value = state
    }

    fun getWishState() = viewModelScope.launch(Dispatchers.IO) {
        val task = wishListRepository
            .getWishItem(
                _offerId.value!!,
                authManager.getCurrentUser()!!.uid
            )
        val result = Tasks.await(task)

        if (task.isSuccessful) {
            updateWishState(result.documents.isNotEmpty())
        } else {
            updateException(task.exception)
        }
    }

    private fun updateWishState(state: Boolean) = viewModelScope.launch(Dispatchers.Main) {
        _wishState.value = state
    }

    fun changeCartState() = viewModelScope.launch(Dispatchers.IO) {
        this@OfferViewModel.getCartState().join()

        if (cartState.value!!) {
            removeCartItem()
        } else {
            addCartItem()
        }
    }

    fun changeWishState() = viewModelScope.launch(Dispatchers.IO) {
        this@OfferViewModel.getWishState().join()

        if (wishState.value!!) {
            removeWishItem()
        } else {
            addWishItem()
        }
    }

    private fun removeWishItem() = viewModelScope.launch(Dispatchers.IO) {
        val task = wishListRepository.removeWishListItem(
            _offerId.value!!,
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
            productId = _offerId.value!!,
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
            productId = _offerId.value!!,
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
            _offerId.value!! + authManager.getCurrentUser()!!.uid
        )
        Tasks.await(task)

        if (task.isSuccessful) {
            updateCartState(false)
        } else {
            updateException(task.exception)
        }
    }

    fun getRelatedOffers() {
        offerRepository.getOffers().addOnSuccessListener {
            it.documents.map {
                it.toObject(Offer::class.java)!!
            }.let {
                _offers.value = it
            }
        }
    }
}