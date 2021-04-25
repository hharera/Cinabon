package com.whiteside.cafe.ui.cart

import com.google.firebase.auth.FirebaseAuth
import com.whiteside.cafe.api.repository.CartRepository
import com.whiteside.cafe.common.BaseListener
import com.whiteside.cafe.model.Item
import com.whiteside.cafe.model.Product
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class CartPresenter @Inject constructor(val cartRepository: CartRepository) {
    val auth by lazy { FirebaseAuth.getInstance() }

    fun addItem(product: Product, response: BaseListener<Product>) {
        response.onLoading()
        addItemToUserCart(product).addOnSuccessListener {
            addCartToProduct(product).addOnSuccessListener {
                response.onSuccess(product)
            }
        }.addOnFailureListener {
            response.onFailed(it)
        }
    }

    private fun addItemToUserCart(product: Product) =
        cartRepository.addItemToUserCart(product)

    private fun addCartToProduct(product: Product) =
        cartRepository.addCartToItem(product)

    fun removeItem(product: Product, response: BaseListener<Product>) {
        response.onLoading()
        removeItemFromUserCart(product).addOnSuccessListener {
            removeCartFromProduct(product).addOnSuccessListener {
                response.onSuccess(product)
            }
        }.addOnFailureListener {
            response.onFailed(CancellationException())
        }
    }

    private fun removeItemFromUserCart(product: Product) =
        cartRepository.removeItemFromUserCart(product)

    private fun removeCartFromProduct(product: Product) =
        cartRepository.removeCartFromProduct(product)

    fun updateQuantity(item: Item, quantity: Int, result: (Int) -> Unit) {
        cartRepository.updateQuantity(item, quantity)
            .addOnSuccessListener {
                result(quantity)
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }

    fun getCartItems(result: (Item) -> Unit) {
        cartRepository
            .getCartItems()
            .addOnSuccessListener {
                it.documents.forEach {
                    result(it.toObject(Item::class.java)!!)
                }
            }.addOnFailureListener {
                it.printStackTrace()
            }
    }

    fun checkItem(product: Product, result: (Boolean) -> Unit) {
        result(product.carts.containsKey(auth.uid))
    }
}