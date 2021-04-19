package com.whiteside.cafe.ui.offer

import android.os.Bundle
import android.os.CountDownTimer
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

        val offerId = intent.extras!!.getString("offerId")!!
        title = findViewById(R.id.title)

        getOffer()
    }

    private fun getOffer() {
        offerPresenter.getOfferById(offerId) {
            offer = it
            setOfferView()
            getProduct()
        }
    }

    private fun getProduct() {
        productPresenter.getProduct(offer.categoryName, offer.productId) {
            product = it
        }
    }

    fun cartClicked(view: View?) {
        cartPresenter.checkItem(product) {
            if (it) {
                cartPresenter.removeItem {
                }
            } else {
                cartPresenter.addItem {

                }

            }
        }
        cartPresenter.removeItem(product) {
            Toast.makeText(this, "Failed to load product", Toast.LENGTH_SHORT).show()
        }

    }

    fun wishClicked(view: View?) {
        if (product.wishes.containsKey(auth.uid)) {
            wishListPresenter.removeItem(product)
        } else {
            wishListPresenter.addItem(product)
        }
    }

    fun goBack(view: View?) {
        finish()
    }

    fun setOfferView() {
        setEndTime()
    }

    private fun setEndTime() {
        object :
            CountDownTimer((offer.endTime.seconds - Timestamp.now().seconds) * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                bind.endTime.text = "Offer is Expired"
            }
        }.start()
        bind.endTime.text =
            "Ends In " + (offer.endTime.seconds - Timestamp.now().seconds) / 86400 + " days"
    }

    override fun onGetProductSuccess(product: Product) {
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

    override fun onGetProductFailed(e: Exception) {
    }

    override fun onRemoveCartItemSuccess() {
        bind.cart.setImageResource(R.drawable.cart)
        Toast.makeText(this, "Product removed from the cart", Toast.LENGTH_SHORT).show()
    }

    override fun onAddWishListItemSuccess() {
        bind.wish.setImageResource(R.drawable.wished)
        Toast.makeText(this, "Product Added to the wishList", Toast.LENGTH_SHORT).show()
    }

    override fun onRemoveCartItemFailed(e: Exception) {
        Toast.makeText(this, "Operation Failed", Toast.LENGTH_SHORT).show()
    }

    override fun onAddWishListItemFailed(e: Exception) {
        Toast.makeText(this, "Operation Failed", Toast.LENGTH_SHORT).show()
    }

    override fun onAddCartItemSuccess() {
        bind.cart.setImageResource(R.drawable.carted)
        Toast.makeText(this@OfferView, "product added to the cart", Toast.LENGTH_SHORT).show()
    }

    override fun onAddCartItemFailed(e: Exception) {}
    override fun onRemoveWishListItemSuccess() {
        bind.wish.setImageResource(R.drawable.wish)
        Toast.makeText(this@OfferView, "product removed from the wishList", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onRemoveWishListItemFailed(e: Exception) {
        Toast.makeText(this, "Operation Failed", Toast.LENGTH_SHORT).show()
    }
}