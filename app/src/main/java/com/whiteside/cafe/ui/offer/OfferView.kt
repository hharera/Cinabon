package com.whiteside.cafe.ui.offer

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.whiteside.cafe.R
import com.whiteside.cafe.adapter.ProductPicturesRecyclerViewAdapter
import com.whiteside.cafe.databinding.ActivityOfferViewBinding
import com.whiteside.cafe.model.Offer
import com.whiteside.cafe.model.Product
import com.whiteside.cafe.ui.cart.CartPresenter
import com.whiteside.cafe.ui.product.ProductPresenter
import com.whiteside.cafe.ui.wishlist.WishListPresenter
import com.whiteside.cafe.utils.Time
import javax.inject.Inject

class OfferView : AppCompatActivity() {

    private lateinit var bind: ActivityOfferViewBinding
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var product: Product
    private lateinit var offer: Offer
    private lateinit var offerId: String

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
        setContentView(R.layout.activity_offer_view)
        auth = FirebaseAuth.getInstance()

        offerId = intent.extras!!.getString("offerId")!!
        title = findViewById(R.id.title)

        getOffer()
    }

    private fun getOffer() {
        offerPresenter.getOfferById(offerId) {
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
        cartPresenter.addItem(product) {
            bind.cart.setImageResource(R.drawable.carted)
            Toast.makeText(this@OfferView, "product added to the cart", Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeCartItem() {
        cartPresenter.removeItem(product) {
            bind.cart.setImageResource(R.drawable.cart)
            Toast.makeText(this@OfferView, "product removed from the cart", Toast.LENGTH_SHORT)
                .show()
        }
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
        wishListPresenter.addItem(product) {
            bind.wish.setImageResource(R.drawable.wished)
            Toast.makeText(this, "Product Added to the wishList", Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeWishListItem() {
        wishListPresenter.removeItem(product) {
            bind.wish.setImageResource(R.drawable.wish)
            Toast.makeText(this@OfferView, "product removed from the wishList", Toast.LENGTH_SHORT)
                .show()
        }
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
        bind.productPics.adapter = ProductPicturesRecyclerViewAdapter(pics, this@OfferView)
        if (product.carts.containsKey(auth.uid)) {
            bind.cart.setImageResource(R.drawable.carted)
        }
        if (product.wishes.containsKey(auth.uid)) {
            bind.wish.setImageResource(R.drawable.wished)
        }
        this.product = product
    }
}