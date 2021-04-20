package com.whiteside.cafe.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.whiteside.cafe.CafeApp
import com.whiteside.cafe.R
import com.whiteside.cafe.model.Item
import com.whiteside.cafe.model.Product
import com.whiteside.cafe.ui.cart.CartPresenter
import com.whiteside.cafe.ui.cart.OnAddCartItem
import com.whiteside.cafe.ui.product.OnGetProductListener
import com.whiteside.cafe.ui.product.ProductPresenter
import com.whiteside.cafe.ui.wishlist.OnRemoveWishListItemListener
import com.whiteside.cafe.ui.wishlist.WishListFragment
import com.whiteside.cafe.ui.wishlist.WishListPresenter

class WishListRecyclerViewAdapter(
    private val list: MutableList<Item>,
    private val context: Context,
    private val application: CafeApp,
    private val wishListFragment: WishListFragment?
) : RecyclerView.Adapter<WishListRecyclerViewAdapter.ViewHolder?>(), OnRemoveWishListItemListener,
    OnAddCartItem {
    private val fStore: FirebaseFirestore?
    private val auth: FirebaseAuth?
    private var wishListPresenter: WishListPresenter
    private var cartPresenter: CartPresenter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.wishlist_item_card_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productPresenter = ProductPresenter()
        with(productPresenter) {
            setListener(object : OnGetProductListener {
                override fun onGetProductSuccess(product: Product) {
                    holder.itemImage.setImageBitmap(
                        BitmapFactory.decodeByteArray(
                            product.productPics[0].toBytes(),
                            0,
                            product.productPics[0].toBytes().size
                        )
                    )
                    holder.title.text = product.title
                    val quantity = list[position].quantity
                    val totalPrice = product.price * quantity!!
                    holder.price.text = "$totalPrice EGP"
                    setListeners(holder, product)
                }

                override fun onGetProductFailed(e: Exception) {}
            })
            getProductInfo(
                list[position].categoryName,
                list[position].productId
            )
        }
    }

    private fun setListeners(holder: ViewHolder, product: Product) {
        holder.remove.setOnClickListener { wishListPresenter.removeItem(product) }
        holder.addToCart.setOnClickListener {
            wishListPresenter.removeItem(product)
            cartPresenter.addItem(product)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onRemoveWishListItemSuccess() {
        wishListFragment!!.getWishList()
    }

    override fun onRemoveWishListItemFailed(e: Exception) {}
    override fun onAddCartItemSuccess() {}
    override fun onAddCartItemFailed(e: Exception) {}
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var title: TextView
        var price: TextView
        var remove: TextView
        var addToCart: TextView

        init {
            itemImage = itemView.findViewById(R.id.item_image)
            title = itemView.findViewById(R.id.title)
            price = itemView.findViewById(R.id.price)
            remove = itemView.findViewById(R.id.remove)
            addToCart = itemView.findViewById(R.id.add_to_cart)
        }
    }

    init {
        fStore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        wishListPresenter = WishListPresenter()
        wishListPresenter.onRemoveWishListItemListener = (this)
        cartPresenter = CartPresenter(application)
        cartPresenter.onAddCartItem = (this)
    }
}