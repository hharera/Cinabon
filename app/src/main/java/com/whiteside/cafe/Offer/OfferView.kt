package com.whiteside.cafe.Offer

import Model.Offer
import Model.Product
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.whiteside.cafe.Cart.CartPresenter
import com.whiteside.cafe.Cart.OnAddCartItem
import com.whiteside.cafe.Cart.OnRemoveCartItem
import com.whiteside.cafe.Offer.OfferView
import com.whiteside.cafe.Product.OnGetProductListener
import com.whiteside.cafe.Product.ProductPresenter
import com.whiteside.cafe.ProductPicturesRecyclerViewAdapter
import com.whiteside.cafe.R
import com.whiteside.cafe.Shop.OnGetOffersListener
import com.whiteside.cafe.WishList.OnAddWishListItem
import com.whiteside.cafe.WishList.OnRemoveWishListItemListener
import com.whiteside.cafe.WishList.WishListPresenter

class OfferView : AppCompatActivity(), OnGetOffersListener, OnAddCartItem, OnRemoveCartItem,
    OnAddWishListItem, OnRemoveWishListItemListener, OnGetProductListener {
    private var auth: FirebaseAuth? = null
    private var wish: ImageView? = null
    private var cart: ImageView? = null
    private var title: TextView? = null
    private var endTime: TextView? = null
    private var price: TextView? = null
    private var productPics: ViewPager2? = null
    private var product: Product? = null
    private var offer: Offer? = null
    private var offerId: String? = null
    private var offerPresenter: OfferPresenter? = null
    private var productPresenter: ProductPresenter? = null
    private var cartPresenter: CartPresenter? = null
    private var wishListPresenter: WishListPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_view)
        auth = FirebaseAuth.getInstance()
        offerId = intent.extras.getString("offerId")
        wish = findViewById(R.id.wish)
        cart = findViewById(R.id.cart)
        title = findViewById(R.id.title)
        productPics = findViewById(R.id.product_pics)
        endTime = findViewById(R.id.end_Time)
        price = findViewById(R.id.price)
        productPresenter = ProductPresenter()
        productPresenter.setListener(this)
        cartPresenter = CartPresenter()
        cartPresenter.setOnAddCartItem(this)
        cartPresenter.setOnRemoveCartItem(this)
        wishListPresenter = WishListPresenter()
        wishListPresenter.setOnAddWishListItem(this)
        wishListPresenter.setOnRemoveWishListItemListener(this)
        offerPresenter = OfferPresenter(this)
        offerPresenter.getOffer(offerId)
    }

    fun cartClicked(view: View?) {
        if (product.getCarts().containsKey(auth.getUid())) {
            cartPresenter.removeItem(product)
        } else {
            cartPresenter.addItem(product)
        }
    }

    fun wishClicked(view: View?) {
        if (product.getWishes().containsKey(auth.getUid())) {
            wishListPresenter.removeItem(product)
        } else {
            wishListPresenter.addItem(product)
        }
    }

    fun goBack(view: View?) {
        finish()
    }

    @SuppressLint("SetTextI18n")
    override fun onGetOfferSuccess(offer: Offer?) {
        this.offer = offer
        setEndTime()
        productPresenter.getProductInfo(offer.getCategoryName(), offer.getProductId())
    }

    @SuppressLint("SetTextI18n")
    private fun setEndTime() {
        object :
            CountDownTimer((offer.getEndTime().seconds - Timestamp.now().seconds) * 1000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                var millisUntilFinished = millisUntilFinished
                val days = millisUntilFinished / 86400000
                millisUntilFinished = millisUntilFinished % 86400000
                val hours = millisUntilFinished / 3600000
                millisUntilFinished = millisUntilFinished % 60000
                val miens = millisUntilFinished / 60000
                endTime.setText("$days days $hours hours $miens Miens")
            }

            override fun onFinish() {
                endTime.setText("Offer is Expired")
            }
        }.start()
        endTime.setText("Ends In " + (offer.getEndTime().seconds - Timestamp.now().seconds) / 86400 + " days")
    }

    override fun onGetOfferFailed(e: Exception?) {}
    override fun onGetProductSuccess(product: Product?) {
        title.setText(product.getTitle())
        price.setText(product.getPrice().toString() + " EGP")
        val pics = product.getProductPics()
        productPics.setAdapter(ProductPicturesRecyclerViewAdapter(pics, this@OfferView))
        if (product.getCarts().containsKey(auth.getUid())) {
            cart.setImageResource(R.drawable.carted)
        }
        if (product.getWishes().containsKey(auth.getUid())) {
            wish.setImageResource(R.drawable.wished)
        }
        this.product = product
    }

    override fun onGetProductFailed(e: Exception?) {
        Toast.makeText(this, "Failed to load product", Toast.LENGTH_SHORT).show()
    }

    override fun onRemoveCartItemSuccess() {
        cart.setImageResource(R.drawable.cart)
        Toast.makeText(this, "Product removed from the cart", Toast.LENGTH_SHORT).show()
    }

    override fun onAddWishListItemSuccess() {
        wish.setImageResource(R.drawable.wished)
        Toast.makeText(this, "Product Added to the wishList", Toast.LENGTH_SHORT).show()
    }

    override fun onRemoveCartItemFailed(e: Exception?) {
        Toast.makeText(this, "Operation Failed", Toast.LENGTH_SHORT).show()
    }

    override fun onAddWishListItemFailed(e: Exception?) {
        Toast.makeText(this, "Operation Failed", Toast.LENGTH_SHORT).show()
    }

    override fun onAddCartItemSuccess() {
        cart.setImageResource(R.drawable.carted)
        Toast.makeText(this@OfferView, "product added to the cart", Toast.LENGTH_SHORT).show()
    }

    override fun onAddCartItemFailed(e: Exception?) {}
    override fun onRemoveWishListItemSuccess() {
        wish.setImageResource(R.drawable.wish)
        Toast.makeText(this@OfferView, "product removed from the wishList", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onRemoveWishListItemFailed(e: Exception?) {
        Toast.makeText(this, "Operation Failed", Toast.LENGTH_SHORT).show()
    }
}