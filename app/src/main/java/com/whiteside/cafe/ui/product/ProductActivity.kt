package com.whiteside.cafe.ui.product

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.whiteside.cafe.R
import com.whiteside.cafe.adapter.ProductPicturesRecyclerViewAdapter
import com.whiteside.cafe.api.repository.AuthManager
import com.whiteside.cafe.common.BaseActivity
import com.whiteside.cafe.common.BaseListener
import com.whiteside.cafe.databinding.ActivityProductViewBinding
import com.whiteside.cafe.model.Product
import com.whiteside.cafe.ui.cart.CartPresenter
import com.whiteside.cafe.ui.make_offer.MakeOffer
import com.whiteside.cafe.ui.wishlist.WishListPresenter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductActivity : BaseActivity() {
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
        bind = ActivityProductViewBinding.inflate(layoutInflater)
        setContentView(bind.root)

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
                true -> cartPresenter.removeItem(product,
                    object : BaseListener<Product> {
                        override fun onSuccess(result: Product) {
                            handleSuccess()
                            bind.cart.setImageResource(R.drawable.cart)
                            Toast.makeText(
                                this@ProductActivity,
                                "Product removed from the cart",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onFailed(exception: Exception) {
                            handleFailure(exception)
                        }

                        override fun onLoading() {
                            handleLoading()
                        }
                    })


                false -> cartPresenter.addItem(product,
                    object : BaseListener<Product> {
                        override fun onSuccess(result: Product) {
                            handleSuccess()
                            bind.cart.setImageResource(R.drawable.carted)
                            Toast.makeText(this@ProductActivity, "Product added to the cart", Toast.LENGTH_SHORT).show()
                        }

                        override fun onFailed(exception: Exception) {
                            handleFailure(exception)
                        }

                        override fun onLoading() {
                            handleLoading()
                        }
                    })

                }
            }
    }

    fun wishClicked(view: View?) {
        wishListPresenter.checkItem(product) {
            when (it) {
                true -> removeWish()
                false -> setWish()
            }
        }
    }

    private fun removeWish() {
        wishListPresenter.removeItem(product,
            object : BaseListener<Product> {
                override fun onSuccess(result: Product) {
                    handleSuccess()
                    bind.wish.setImageResource(R.drawable.wish)
                    Toast.makeText(
                        this@ProductActivity,
                        "Product removed from the WishList",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onFailed(exception: Exception) {
                    handleFailure(exception)
                }

                override fun onLoading() {
                    handleLoading()
                }
            })
    }

    private fun setWish() {
        wishListPresenter.addItem(product,
            object : BaseListener<Product> {
                override fun onSuccess(result: Product) {
                    handleSuccess()
                    bind.wish.setImageResource(com.whiteside.cafe.R.drawable.wished)
                    Toast.makeText(
                        this@ProductActivity,
                        "Product added to the WishList",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onFailed(exception: Exception) {
                    handleFailure(exception)
                }

                override fun onLoading() {
                    handleLoading()
                }
            })

    }

    fun goBack(view: View?) {
        finish()
    }

    fun updateView() {
        val uid = authManager.getCurrentUser()!!.uid

        if (product.carts.containsKey(uid)) {
            bind.cart.setImageResource(R.drawable.carted)
        }
        if (product.wishes.containsKey(uid)) {
            bind.wish.setImageResource(R.drawable.wished)
        }
        bind.title.text = product.title
        bind.price.text = "${product.price} EGP"
        val pics = product.productPics
        bind.productPics.adapter =
            ProductPicturesRecyclerViewAdapter(pics)
    }

    fun makeOffer(view: View) {
        val intent = Intent(this, MakeOffer::class.java)
        intent.putExtra("productId", product.productId)
        intent.putExtra("categoryName", product.categoryName)
        startActivity(intent)
    }
}