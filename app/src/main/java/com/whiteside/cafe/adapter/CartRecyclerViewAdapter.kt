package com.whiteside.cafe.adapter

import android.app.AlertDialog
import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.whiteside.cafe.common.BaseListener
import com.whiteside.cafe.common.BaseViewHolder
import com.whiteside.cafe.databinding.CartItemCardViewBinding
import com.whiteside.cafe.model.Item
import com.whiteside.cafe.model.Product
import com.whiteside.cafe.ui.cart.CartPresenter
import com.whiteside.cafe.ui.product.ProductPresenter
import com.whiteside.cafe.utils.BlobBitmap
import javax.inject.Inject

class CartRecyclerViewAdapter @Inject constructor(
    var cartPresenter: CartPresenter,
    var productPresenter: ProductPresenter
) : RecyclerView.Adapter<CartRecyclerViewAdapter.ViewViewHolder>() {

    var list: ArrayList<Item> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewViewHolder {
        return ViewViewHolder(
            CartItemCardViewBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ViewViewHolder, position: Int) {
        productPresenter.getProduct(list[position].categoryName, list[position].productId) {
            holder.updateView(it, list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class ViewViewHolder(val bind: CartItemCardViewBinding) : BaseViewHolder(bind) {
        fun updateView(product: Product, item: Item) {
            bind.itemImage.setImageBitmap(BlobBitmap.convertBlobToBitmap(product.productPics[0]))
            bind.title.text = product.title
            bind.quantity.text = item.quantity.toString()
            bind.totalPrice.text = "${product.price * item.quantity!!}"

            bind.edit.setOnClickListener {
                showDialog(item)
            }

            bind.remove.setOnClickListener {
                cartPresenter.removeItem(product,
                    object : BaseListener<Product> {
                        override fun onSuccess(result: Product) {
                            handleSuccess()
                            bind.root.removeAllViews()
                            notifyDataSetChanged()
                            Toast.makeText(
                                bind.root.context,
                                "Item removed from cart",
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

        private fun showDialog(item: Item) {
            val editText = EditText(bind.root.context)
            editText.inputType = InputType.TYPE_CLASS_NUMBER

            AlertDialog.Builder(bind.root.context)
                .setView(editText)
                .setTitle("Enter Quantity")
                .setPositiveButton("Change") { _d, _ ->

                    cartPresenter.updateQuantity(item, editText.text.toString().toInt()) {
                        bind.quantity.text = editText.text.toString()
                        _d.cancel()
                    }

                }.setNegativeButton("Cancel") { _d, _ ->
                    _d.cancel()
                }.show()
        }
    }
}