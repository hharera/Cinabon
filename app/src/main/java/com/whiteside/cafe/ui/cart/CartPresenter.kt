package com.whiteside.cafe.ui.cart

import com.google.firebase.auth.FirebaseAuth
import com.whiteside.cafe.common.firebase.FirebaseCartRepository
import com.whiteside.cafe.model.Item
import com.whiteside.cafe.model.Product
import javax.inject.Inject

class CartPresenter @Inject constructor(private val cartRepository: FirebaseCartRepository) {
    val auth by lazy { FirebaseAuth.getInstance() }

    fun addItem(product: Product, result: (Void) -> (Unit)) {
        addItemToUserCart(product, result)
    }

    private fun addItemToUserCart(product: Product, result: (Void) -> Unit) {
        cartRepository.addItemToUserCart(product)
            .addOnSuccessListener {
                addCartToProduct(product, result)
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }

    private fun addCartToProduct(product: Product, result: (Void) -> Unit) {
        cartRepository.addCartToItem(product)
            .addOnSuccessListener {
                result(it)
            }
            .addOnFailureListener {
                removeItemFromUserCart(product, result)
                it.printStackTrace()
            }
    }

    fun removeItem(product: Product, result: (Void) -> Unit) {
        removeItemFromUserCart(product, result)
    }

    private fun removeItemFromUserCart(product: Product, result: (Void) -> Unit) {
        cartRepository.removeItemFromUserCart(product)
            .addOnSuccessListener {
                removeCartFromProduct(product, result)
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }

    private fun removeCartFromProduct(product: Product, result: (Void) -> Unit) {
        cartRepository.removeCartFromProduct(product)
            .addOnSuccessListener {
            }
            .addOnFailureListener {
                addItemToUserCart(product, result)
                it.printStackTrace()
            }
    }

    fun updateQuantity(item: Item, quantity: Int, result: (Int) -> Void) {
        cartRepository.updateQuantity(item, quantity)
            .addOnSuccessListener {
                result(quantity)
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }

    fun getCartItems(result: (Item) -> Unit, emptyList: (Boolean) -> Unit) {
        cartRepository
            .getCartItems()
            .addOnSuccessListener {
                it.documents.forEach {
                    result(it.toObject(Item::class.java)!!)
                }
                if (it.isEmpty) {
                    emptyList(true)
                }
            }.addOnFailureListener {
                it.printStackTrace()
            }
    }

    fun checkItem(product: Product, result: (Boolean) -> Unit) {
        if (product.carts.containsKey(auth.uid)) {
            result(true)
        } else {
            result(false)
        }
    }
}