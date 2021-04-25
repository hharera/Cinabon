package com.whiteside.cafe.ui.offer

import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.Timestamp
import com.whiteside.cafe.R
import com.whiteside.cafe.adapter.ProductPicturesRecyclerViewAdapter
import com.whiteside.cafe.common.BaseActivity
import com.whiteside.cafe.common.BaseListener
import com.whiteside.cafe.databinding.ActivityOfferViewBinding
import com.whiteside.cafe.model.Offer
import com.whiteside.cafe.model.Product
import com.whiteside.cafe.ui.cart.CartPresenter
import com.whiteside.cafe.ui.product.ProductPresenter
import com.whiteside.cafe.ui.wishlist.WishListPresenter
import com.whiteside.cafe.utils.Time
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint

class OfferActivity : BaseActivity() {

    private lateinit var bind: ActivityOfferViewBinding

    private lateinit var product: Product
    private lateinit var offer: Offer
    private lateinit var offerId: String
    private lateinit var offerType: String

    @Inject
    lateinit var offerPresenter: OfferPresenter

    @Inject
    lateinit var productPresenter: ProductPresenter

    @Inject
    lateinit var cartPresenter: CartPresenter

    @Inject
    lateinit var wishListPresenter: WishListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityOfferViewBinding.inflate(layoutInflater)
        setContentView(bind.root)

        offerId = intent.extras!!.getString("offerId")!!
        offerType = intent.extras!!.getString("offerType")!!

        when (offerType) {
            "BestOffers" -> getBestOffer()
            "LastOffers" -> getLastOffer()
        }
    }

    private fun getBestOffer() {
        offerPresenter.getBestOffer(offerId) {
            offer = it
            updateOfferView()
            getProduct()
        }
    }

    private fun getLastOffer() {
        offerPresenter.getLastOffer(offerId) {
            offer = it
            updateOfferView()
            getProduct()
        }
    }

    private fun getProduct() {
        productPresenter.getProduct(offer.categoryName, offer.productId) {
            product = it
            updateProductView()
        }
    }

    fun cartClicked(view: View?) {
        cartPresenter.checkItem(product) {
            when (it) {
                true -> removeCartItem()
                false -> addCartItem()
            }
        }
    }

    private fun addCartItem() {
        cartPresenter.addItem(product,

            object : BaseListener<Product> {
                override fun onSuccess(result: Product) {
                    handleSuccess()
                    bind.cart.setImageResource(R.drawable.carted)
                    Toast.makeText(
                        this@OfferActivity,
                        "product added to the cart",
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

    private fun removeCartItem() {
        cartPresenter.removeItem(product,


            object : BaseListener<Product> {
                override fun onSuccess(result: Product) {
                    handleSuccess()
                    bind.cart.setImageResource(R.drawable.cart)
                    Toast.makeText(
                        this@OfferActivity,
                        "product removed from the cart",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onFailed(exception: Exception) {
                    handleFailure(exception)
                }

                override fun onLoading() {
                    handleLoading()
                }
            })
    }

    fun wishClicked(view: View?) {
        wishListPresenter.checkItem(product) {
            when (it) {
                true -> removeWishListItem()
                false -> addWishListItem()
            }
        }
    }

    private fun addWishListItem() {
        wishListPresenter.addItem(product,

            object : BaseListener<Product> {
                override fun onSuccess(result: Product) {
                    handleSuccess()
                    bind.wish.setImageResource(R.drawable.wished)
                    Toast.makeText(
                        this@OfferActivity,
                        "Product Added to the wishList",
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

    private fun removeWishListItem() {
        wishListPresenter.removeItem(product,

            object : BaseListener<Product> {
                override fun onSuccess(result: Product) {
                    handleSuccess()
                    bind.wish.setImageResource(R.drawable.wish)
                    Toast.makeText(
                        this@OfferActivity,
                        "product removed from the wishList",
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

    fun updateOfferView() {
        bind.endTime.text =
            Time.timeStampToCountDown(offer.endTime.seconds - Timestamp.now().seconds)
    }

    fun updateProductView() {
        bind.title.text = product.title
        bind.price.text = "${product.price} EGP"
        val pics = product.productPics
        bind.productPics.adapter = ProductPicturesRecyclerViewAdapter(pics)

        cartPresenter.checkItem(product) {
            if (it) {
                bind.cart.setImageResource(R.drawable.carted)
            }
        }

        wishListPresenter.checkItem(product) {
            if (it) {
                bind.wish.setImageResource(R.drawable.wished)
            }
        }
    }
}