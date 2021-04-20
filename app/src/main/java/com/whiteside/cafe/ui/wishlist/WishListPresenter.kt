package com.whiteside.cafe.ui.wishlist

import com.google.firebase.auth.FirebaseAuth
import com.whiteside.cafe.common.firebase.FirebaseWishListRepository
import com.whiteside.cafe.model.Item
import com.whiteside.cafe.model.Product
import javax.inject.Inject

class WishListPresenter @Inject constructor(val wishListRepository: FirebaseWishListRepository) {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun addItem(product: Product, result: (Void) -> (Unit)) {
        addItemToUserWishList(product, result)
    }

    private fun addItemToUserWishList(product: Product, result: (Void) -> Unit) {
        wishListRepository.addItemToUserWishList(product)
            .addOnSuccessListener {
                addWishListToProduct(product, result)
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }

    private fun addWishListToProduct(product: Product, result: (Void) -> Unit) {
        wishListRepository.addWishListToProduct(product)
            .addOnSuccessListener {
                result(it)
            }
            .addOnFailureListener {
                removeItemFromUserWishList(product, result)
                it.printStackTrace()
            }
    }

    fun removeItem(product: Product, result: (Void) -> Unit) {
        removeItemFromUserWishList(product, result)
    }

    private fun removeItemFromUserWishList(product: Product, result: (Void) -> Unit) {
        wishListRepository.removeItemFromUserWishList(product)
            .addOnSuccessListener {
                removeWishListFromProduct(product, result)
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }

    private fun removeWishListFromProduct(product: Product, result: (Void) -> Unit) {
        wishListRepository.removeWishListFromProduct(product)
            .addOnSuccessListener {
            }
            .addOnFailureListener {
                addItemToUserWishList(product, result)
                it.printStackTrace()
            }
    }

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
        if (product.wishes.containsKey(auth.uid)) {
            result(true)
        } else {
            result(false)
        }
    }
}