package com.whiteside.cafe.ui.product

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.whiteside.cafe.R
import com.whiteside.cafe.adapter.ProductPicturesRecyclerViewAdapter
import com.whiteside.cafe.api.repository.AuthManager
import com.whiteside.cafe.databinding.ActivityProductViewBinding
import com.whiteside.cafe.model.Product
import com.whiteside.cafe.ui.cart.CartPresenter
import com.whiteside.cafe.ui.wishlist.WishListPresenter
import javax.inject.Inject

class ProductView : AppCompatActivity() {
    private lateinit var product: Product

    @Inject
    lateinit var cartPresenter: CartPresenter

    @Inject
    lateinit var productPresenter: ProductPresenter

    @Inject
    lateinit var wishListPresenter: WishListPresenter

    @Inject
    lateinit var authManager: AuthManager

    lateinit var bind: ActivityProductViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityProductViewBinding.inflate(layoutInflater)

        val productId = intent.extras!!.getString("productId")!!
        val categoryName = intent.extras!!.getString("categoryName")!!

        productPresenter.getProduct(categoryName, productId) {
            product = it
            updateView()
        }
    }

    fun cartClicked(view: View?) {
        cartPresenter.checkItem(product) {
            when (it) {
                true -> cartPresenter.removeItem(product) {
                    bind.cart.setImageResource(R.drawable.cart)
                    Toast.makeText(this, "Product removed from the cart", Toast.LENGTH_SHORT).show()
                }

                false -> cartPresenter.addItem(product) {
                    bind.cart.setImageResource(R.drawable.carted)
                    Toast.makeText(this, "Offer added to the cart", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun wishClicked(view: View?) {
        cartPresenter.checkItem(product) {
            when (it) {
                true -> wishListPresenter.removeItem(product) {
                    bind.wish.setImageResource(R.drawable.wish)
                    Toast.makeText(this, "Product removed to the WishList", Toast.LENGTH_SHORT)
                        .show()
                }

                false -> wishListPresenter.addItem(product) {
                    bind.wish.setImageResource(R.drawable.wished)
                    Toast.makeText(
                        this@ProductView,
                        "Product added to the WishList",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun goBack(view: View?) {
        finish()
    }

    fun updateView() {
        val uid = authManager.getCurrentUser().uid

        if (product.carts.containsKey(uid)) {
            bind.cart.setImageResource(R.drawable.carted)
        }
        if (product.wishes.containsKey(uid)) {
            bind.wish.setImageResource(R.drawable.wished)
        }
        bind.title.text = product.title
        bind.price.text = product.price.toString() + " EGP"
        val pics = product.productPics
        bind.productPics.adapter = ProductPicturesRecyclerViewAdapter(pics, this@ProductView)
    }
}