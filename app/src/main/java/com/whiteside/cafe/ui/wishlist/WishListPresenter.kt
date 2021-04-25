package com.whiteside.cafe.ui.wishlist

import com.google.firebase.auth.FirebaseAuth
import com.whiteside.cafe.api.firebase.FirebaseWishListRepository
import com.whiteside.cafe.common.BaseListener
import com.whiteside.cafe.model.Item
import com.whiteside.cafe.model.Product
import javax.inject.Inject

class WishListPresenter @Inject constructor(val wishListRepository: FirebaseWishListRepository) {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun addItem(product: Product, response: BaseListener<Product>) {
        response.onLoading()
        addItemToUserWishList(product).addOnSuccessListener {
            addWishListToProduct(product).addOnSuccessListener {
                onSuccess(product, response)
            }
        }.addOnFailureListener {
            onFailed(it, response)
        }
    }

    private fun onSuccess(product: Product, response: BaseListener<Product>) {
        response.onSuccess(product)
    }

    private fun onFailed(exception: Exception, response: BaseListener<Product>) {
        response.onFailed(exception)
    }

    private fun addItemToUserWishList(product: Product) =
        wishListRepository.addItemToUserWishList(product)

    private fun addWishListToProduct(product: Product) =
        wishListRepository.addWishListToProduct(product)


    fun removeItem(product: Product, response: BaseListener<Product>) {
        response.onLoading()
        removeItemFromUserWishList(product).addOnSuccessListener {
            removeWishListFromProduct(product).addOnSuccessListener {
                onSuccess(product, response)
            }
        }.addOnFailureListener {
            onFailed(it, response)
        }
    }

    private fun removeItemFromUserWishList(product: Product) =
        wishListRepository.removeItemFromUserWishList(product)

    private fun removeWishListFromProduct(product: Product) =
        wishListRepository.removeWishListFromProduct(product)

    fun getWishList(result: (Item) -> Unit) {
        wishListRepository.getWishListItems()
            .addOnSuccessListener {
                it.documents.forEach {
                    result(it.toObject(Item::class.java)!!)
                }
            }.addOnFailureListener {
                it.printStackTrace()
            }
    }

    fun checkItem(product: Product, result: (Boolean) -> Unit) {
        result(product.wishes.containsKey(auth.uid))
    }
}