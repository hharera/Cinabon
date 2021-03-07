package com.whiteside.cafe.WishList

import Model.Item
import Model.Product
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
import com.whiteside.cafe.Cart.CartPresenter
import com.whiteside.cafe.Cart.OnAddCartItem
import com.whiteside.cafe.Product.OnGetProductListener
import com.whiteside.cafe.Product.ProductPresenter
import com.whiteside.cafe.R

class WishListRecyclerViewAdapter(
    private val list: MutableList<Item?>?,
    private val context: Context?,
    private val wishListFragment: WishListFragment?
) : RecyclerView.Adapter<WishListRecyclerViewAdapter.ViewHolder?>(), OnRemoveWishListItemListener,
    OnAddCartItem {
    private val fStore: FirebaseFirestore?
    private val auth: FirebaseAuth?
    private val wishListPresenter: WishListPresenter?
    private val cartPresenter: CartPresenter?
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.wishlist_item_card_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productPresenter = ProductPresenter()
        productPresenter.setListener(object : OnGetProductListener {
            override fun onGetProductSuccess(product: Product?) {
                holder.itemImage.setImageBitmap(
                    BitmapFactory.decodeByteArray(
                        product.getMainPic().toBytes(), 0, product.getMainPic().toBytes().size
                    )
                )
                holder.title.setText(product.getTitle())
                val quantity = list.get(position).getQuantity()
                val totalPrice = product.getPrice() * quantity
                holder.price.setText("$totalPrice EGP")
                setListeners(holder, product)
            }

            override fun onGetProductFailed(e: Exception?) {}
        })
        productPresenter.getProduct(list.get(position))
    }

    private fun setListeners(holder: ViewHolder?, product: Product?) {
        holder.remove.setOnClickListener(View.OnClickListener { wishListPresenter.removeItem(product) })
        holder.addToCart.setOnClickListener(View.OnClickListener {
            wishListPresenter.removeItem(product)
            cartPresenter.addItem(product)
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onRemoveWishListItemSuccess() {
        wishListFragment.updateView()
    }

    override fun onRemoveWishListItemFailed(e: Exception?) {}
    override fun onAddCartItemSuccess() {}
    override fun onAddCartItemFailed(e: Exception?) {}
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView?
        var title: TextView?
        var price: TextView?
        var remove: TextView?
        var addToCart: TextView?

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
        wishListPresenter.setOnRemoveWishListItemListener(this)
        cartPresenter = CartPresenter()
        cartPresenter.setOnAddCartItem(this)
    }
}