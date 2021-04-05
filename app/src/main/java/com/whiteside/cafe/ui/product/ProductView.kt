package com.whiteside.cafe.ui.product

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseAuth
import com.whiteside.cafe.R
import com.whiteside.cafe.adapter.ProductPicturesRecyclerViewAdapter
import com.whiteside.cafe.model.Product
import com.whiteside.cafe.ui.cart.CartPresenter
import com.whiteside.cafe.ui.cart.OnAddCartItem
import com.whiteside.cafe.ui.cart.OnRemoveCartItem
import com.whiteside.cafe.ui.wishList.OnAddWishListItem
import com.whiteside.cafe.ui.wishList.OnRemoveWishListItemListener
import com.whiteside.cafe.ui.wishList.WishListPresenter

class ProductView : AppCompatActivity(), OnRemoveCartItem, OnAddCartItem, OnAddWishListItem,

    OnRemoveWishListItemListener, OnGetProductListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var wish: ImageView
    private lateinit var cart: ImageView
    private lateinit var title: TextView
    private lateinit var price: TextView
    private lateinit var productPics: ViewPager2
    private lateinit var product: Product
    private lateinit var cartPresenter: CartPresenter
    private lateinit var wishListPresenter: WishListPresenter
    private lateinit var productPresenter: ProductPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_view)
        auth = FirebaseAuth.getInstance()
        val productId = intent.extras!!.getString("productId")!!
        val categoryName = intent.extras!!.getString("categoryName")!!
        wish = findViewById(R.id.wish)
        cart = findViewById(R.id.cart)
        title = findViewById(R.id.title)
        productPics = findViewById(R.id.product_pics)
        price = findViewById(R.id.price)
        productPresenter = ProductPresenter()
        productPresenter.setListener(this)
        productPresenter.getProductInfo(categoryName, productId)
        cartPresenter = CartPresenter()
        cartPresenter.onRemoveCartItem = (this)
        cartPresenter.onAddCartItem = (this)
        wishListPresenter = WishListPresenter()
        wishListPresenter.onRemoveWishListItemListener = (this)
        wishListPresenter.onAddWishListItem = (this)
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

    override fun onRemoveCartItemSuccess() {
        cart.setImageResource(R.drawable.cart)
        Toast.makeText(this, "Product removed from the cart", Toast.LENGTH_SHORT).show()
    }

    override fun onRemoveCartItemFailed(e: Exception) {}
    override fun onAddCartItemSuccess() {
        cart.setImageResource(R.drawable.carted)
        Toast.makeText(this, "Offer added to the cart", Toast.LENGTH_SHORT).show()
    }

    override fun onAddCartItemFailed(e: Exception) {}

    @SuppressLint("SetTextI18n")
    override fun onGetProductSuccess(product: Product) {
        this.product = product
        if (product.carts.containsKey(auth.uid)) {
            cart.setImageResource(R.drawable.carted)
        }
        if (product.wishes.containsKey(auth.uid)) {
            wish.setImageResource(R.drawable.wished)
        }
        title.text = product.title
        price.text = product.price.toString() + " EGP"
        val pics = product.productPics
        productPics.adapter = ProductPicturesRecyclerViewAdapter(pics, this@ProductView)
    }

    override fun onGetProductFailed(e: Exception) {}
    override fun onAddWishListItemSuccess() {
        wish.setImageResource(R.drawable.wished)
        Toast.makeText(this@ProductView, "Product added to the WishList", Toast.LENGTH_SHORT).show()
    }

    override fun onAddWishListItemFailed(e: Exception) {}
    override fun onRemoveWishListItemSuccess() {
        wish.setImageResource(R.drawable.wish)
        Toast.makeText(this, "Product removed to the WishList", Toast.LENGTH_SHORT).show()
    }

    override fun onRemoveWishListItemFailed(e: Exception) {}
}