package com.whiteside.cafe.Product

import Model.Product
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseAuth
import com.whiteside.cafe.Cart.CartPresenter
import com.whiteside.cafe.Cart.OnAddCartItem
import com.whiteside.cafe.Cart.OnRemoveCartItem
import com.whiteside.cafe.Product.ProductView
import com.whiteside.cafe.ProductPicturesRecyclerViewAdapter
import com.whiteside.cafe.R
import com.whiteside.cafe.WishList.OnAddWishListItem
import com.whiteside.cafe.WishList.OnRemoveWishListItemListener
import com.whiteside.cafe.WishList.WishListPresenter

class ProductView : AppCompatActivity(), OnRemoveCartItem, OnAddCartItem, OnAddWishListItem,
    OnRemoveWishListItemListener, OnGetProductListener {
    private var auth: FirebaseAuth? = null
    private var wish: ImageView? = null
    private var cart: ImageView? = null
    private var title: TextView? = null
    private var price: TextView? = null
    private var productPics: ViewPager2? = null
    private var product: Product? = null
    private var cartPresenter: CartPresenter? = null
    private var wishListPresenter: WishListPresenter? = null
    private var productPresenter: ProductPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_view)
        auth = FirebaseAuth.getInstance()
        val productId = intent.extras.getString("productId")
        val categoryName = intent.extras.getString("categoryName")
        wish = findViewById(R.id.wish)
        cart = findViewById(R.id.cart)
        title = findViewById(R.id.title)
        productPics = findViewById(R.id.product_pics)
        price = findViewById(R.id.price)
        productPresenter = ProductPresenter()
        productPresenter.setListener(this)
        productPresenter.getProductInfo(categoryName, productId)
        cartPresenter = CartPresenter()
        cartPresenter.setOnRemoveCartItem(this)
        cartPresenter.setOnAddCartItem(this)
        wishListPresenter = WishListPresenter()
        wishListPresenter.setOnRemoveWishListItemListener(this)
        wishListPresenter.setOnAddWishListItem(this)
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

    override fun onRemoveCartItemSuccess() {
        cart.setImageResource(R.drawable.cart)
        Toast.makeText(this, "Product removed from the cart", Toast.LENGTH_SHORT).show()
    }

    override fun onRemoveCartItemFailed(e: Exception?) {}
    override fun onAddCartItemSuccess() {
        cart.setImageResource(R.drawable.carted)
        Toast.makeText(this, "Offer added to the cart", Toast.LENGTH_SHORT).show()
    }

    override fun onAddCartItemFailed(e: Exception?) {}

    @SuppressLint("SetTextI18n")
    override fun onGetProductSuccess(product: Product?) {
        this.product = product
        if (product.getCarts().containsKey(auth.getUid())) {
            cart.setImageResource(R.drawable.carted)
        }
        if (product.getWishes().containsKey(auth.getUid())) {
            wish.setImageResource(R.drawable.wished)
        }
        title.setText(product.getTitle())
        price.setText(product.getPrice().toString() + " EGP")
        val pics = product.getProductPics()
        productPics.setAdapter(ProductPicturesRecyclerViewAdapter(pics, this@ProductView))
    }

    override fun onGetProductFailed(e: Exception?) {}
    override fun onAddWishListItemSuccess() {
        wish.setImageResource(R.drawable.wished)
        Toast.makeText(this@ProductView, "Product added to the WishList", Toast.LENGTH_SHORT).show()
    }

    override fun onAddWishListItemFailed(e: Exception?) {}
    override fun onRemoveWishListItemSuccess() {
        wish.setImageResource(R.drawable.wish)
        Toast.makeText(this, "Product removed to the WishList", Toast.LENGTH_SHORT).show()
    }

    override fun onRemoveWishListItemFailed(e: Exception?) {}
}