package com.whiteside.cafe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.whiteside.cafe.common.BaseListener
import com.whiteside.cafe.common.BaseViewHolder
import com.whiteside.cafe.databinding.WishlistItemCardViewBinding
import com.whiteside.cafe.model.Item
import com.whiteside.cafe.model.Product
import com.whiteside.cafe.ui.cart.CartPresenter
import com.whiteside.cafe.ui.product.ProductPresenter
import com.whiteside.cafe.ui.wishlist.WishListPresenter
import com.whiteside.cafe.utils.BlobBitmap
import javax.inject.Inject

class WishListRecyclerViewAdapter @Inject constructor(
    var productPresenter: ProductPresenter,
    var cartPresenter: CartPresenter,
    var wishListPresenter: WishListPresenter
) : RecyclerView.Adapter<WishListRecyclerViewAdapter.ViewHolder?>() {

    var list: ArrayList<Item> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            WishlistItemCardViewBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        productPresenter.getProduct(list[position].categoryName, list[position].productId) {
            holder.updateView(it, list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val bind: WishlistItemCardViewBinding) :
        BaseViewHolder(bind) {

        fun updateView(product: Product, item: Item) {
            bind.itemImage.setImageBitmap(BlobBitmap.convertBlobToBitmap(product.productPics[0]))
            bind.title.text = product.title
            bind.price.text = "${product.price * item.quantity!!}"

            bind.remove.setOnClickListener {
                wishListPresenter.removeItem(product,
                    object : BaseListener<Product> {
                        override fun onSuccess(result: Product) {
                            handleSuccess()

                            bind.root.removeAllViews()
                            Toast.makeText(
                                bind.root.context,
                                "Item removed from wishlist",
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

            bind.addToCart.setOnClickListener {
                cartPresenter.addItem(product,
                    object : BaseListener<Product> {
                        override fun onSuccess(result: Product) {
                        }

                        override fun onFailed(exception: Exception) {
                            handleFailure(exception)
                        }

                        override fun onLoading() {
                        }
                    })

                wishListPresenter.removeItem(product,
                    object : BaseListener<Product> {
                        override fun onSuccess(result: Product) {
                            handleSuccess()

                            bind.root.removeAllViews()
                            Toast.makeText(
                                bind.root.context,
                                "Item added from wishlist",
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
        }
    }
}
