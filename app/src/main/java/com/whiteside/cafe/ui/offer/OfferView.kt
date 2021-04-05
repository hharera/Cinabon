package com.whiteside.cafe.ui.offer

import android.annotation.SuppressLint
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
import com.whiteside.cafe.ui.cart.OnAddCartItem
import com.whiteside.cafe.ui.cart.OnRemoveCartItem
import com.whiteside.cafe.ui.product.OnGetProductListener
import com.whiteside.cafe.ui.product.ProductPresenter
import com.whiteside.cafe.ui.shop.OnGetOffersListener
import com.whiteside.cafe.ui.wishList.OnAddWishListItem
import com.whiteside.cafe.ui.wishList.OnRemoveWishListItemListener
import com.whiteside.cafe.ui.wishList.WishListPresenter

class OfferView : AppCompatActivity(), OnGetOffersListener, OnAddCartItem, OnRemoveCartItem,
    OnAddWishListItem, OnRemoveWishListItemListener, OnGetProductListener {

    private lateinit var bind: ActivityOfferViewBinding
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var product: Product
    private lateinit var offer: Offer
    private lateinit var offerId: String
    private lateinit var offerPresenter: OfferPresenter
    private lateinit var productPresenter: ProductPresenter
    private lateinit var cartPresenter: CartPresenter
    private lateinit var wishListPresenter: WishListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_view)
        auth = FirebaseAuth.getInstance()

        val offerId = intent.extras!!.getString("offerId")!!
        title = findViewById(R.id.title)

        productPresenter = ProductPresenter()
        productPresenter.setListener(this)
        cartPresenter = CartPresenter()
        cartPresenter.onAddCartItem = (this)
        cartPresenter.onRemoveCartItem = (this)
        wishListPresenter = WishListPresenter()
        wishListPresenter.onAddWishListItem = (this)
        wishListPresenter.onRemoveWishListItemListener = (this)
        offerPresenter = OfferPresenter(this)
        offerPresenter.getOffer(offerId)
    }

    fun cartClicked(view: View?) {
        if (product.carts.containsKey(auth.uid)) {
            cartPresenter.removeItem(product)
        } else {
            cartPresenter.addItem(product)
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

    @SuppressLint("SetTextI18n")
    override fun onGetOfferSuccess(offer: Offer) {
        this.offer = offer
        setEndTime()
        productPresenter.getProductInfo(offer.categoryName, offer.productId)
    }

    @SuppressLint("SetTextI18n")
    private fun setEndTime() {
        object :
            CountDownTimer((offer.endTime.seconds - Timestamp.now().seconds) * 1000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                var millisUntilFinished = millisUntilFinished
                val days = millisUntilFinished / 86400000
                millisUntilFinished = millisUntilFinished % 86400000
                val hours = millisUntilFinished / 3600000
                millisUntilFinished = millisUntilFinished % 60000
                val miens = millisUntilFinished / 60000
                bind.endTime.text = "$days days $hours hours $miens Miens"
            }

            override fun onFinish() {
                bind.endTime.text = "Offer is Expired"
            }
        }.start()
        bind.endTime.text =
            "Ends In " + (offer.endTime.seconds - Timestamp.now().seconds) / 86400 + " days"
    }

    override fun onGetOfferFailed(e: Exception) {}
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
        Toast.makeText(this, "Failed to load product", Toast.LENGTH_SHORT).show()
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