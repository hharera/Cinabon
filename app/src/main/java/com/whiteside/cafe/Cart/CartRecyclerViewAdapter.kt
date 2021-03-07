package com.whiteside.cafe.Cart

import Model.Item
import Model.Product
import android.app.AlertDialog
import android.content.Context
import android.graphics.BitmapFactory
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.whiteside.cafe.Product.OnGetProductListener
import com.whiteside.cafe.Product.ProductPresenter
import com.whiteside.cafe.R

class CartRecyclerViewAdapter(
    private val list: MutableList<Item?>?,
    private val context: Context?,
    private val cartFragment: CartFragment?
) : RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder?>(), OnRemoveCartItem {
    private val fStore: FirebaseFirestore?
    private val auth: FirebaseAuth?
    private val cartPresenter: CartPresenter?
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cart_item_card_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productPresenter = ProductPresenter()
        productPresenter.setListener(object : OnGetProductListener {
            override fun onGetProductSuccess(product: Product?) {
                holder.imageView.setImageBitmap(
                    BitmapFactory.decodeByteArray(
                        product.getMainPic().toBytes(), 0, product.getMainPic().toBytes().size
                    )
                )
                holder.title.setText(product.getTitle())
                val quantity = list.get(position).getQuantity()
                holder.quantity.setText(quantity.toString())
                val totalPrice = product.getPrice() * quantity
                holder.total_price.setText("$totalPrice EGP")
                cartFragment.editTotalBill(totalPrice.toDouble())
                setListener(holder, position, product)
            }

            override fun onGetProductFailed(e: Exception?) {}
        })
        productPresenter.getProduct(list.get(position))
    }

    private fun setListener(holder: ViewHolder?, position: Int, product: Product?) {
        setEditListener(holder, position, product.getPrice())
        setRemoveListener(holder, product)
    }

    private fun setRemoveListener(holder: ViewHolder?, product: Product?) {
        holder.remove.setOnClickListener(View.OnClickListener { cartPresenter.removeItem(product) })
    }

    private fun setEditListener(holder: ViewHolder?, position: Int, price: Float) {
        holder.edit.setOnClickListener(View.OnClickListener {
            editClicked(
                holder,
                position,
                price
            )
        })
    }

    private fun editClicked(holder: ViewHolder?, position: Int, price: Float) {
        val builder = AlertDialog.Builder(context)
        val editText = EditText(context)
        editText.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(editText)
        builder.setTitle("Enter Quantity")
        builder.setPositiveButton("Change") { dialog, which ->
            val quantity = editText.text.toString().toInt()
            holder.quantity.setText(quantity.toString())
            holder.total_price.setText(quantity * price.toString() + " EGP")
            cartPresenter.updateQuantity(list.get(position), quantity)
            cartFragment.editTotalBill((quantity * price).toDouble())
        }
        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
        builder.show()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onRemoveCartItemSuccess() {
        cartFragment.updateView()
    }

    override fun onRemoveCartItemFailed(e: Exception?) {}
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView?
        var title: TextView?
        var total_price: TextView?
        var quantity: TextView?
        var edit: TextView?
        var remove: TextView?

        init {
            imageView = itemView.findViewById(R.id.item_image)
            title = itemView.findViewById(R.id.title)
            total_price = itemView.findViewById(R.id.total_price)
            quantity = itemView.findViewById(R.id.quantity)
            edit = itemView.findViewById(R.id.edit)
            remove = itemView.findViewById(R.id.remove)
        }
    }

    init {
        fStore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        cartPresenter = CartPresenter()
        cartPresenter.setOnRemoveCartItem(this)
    }
}