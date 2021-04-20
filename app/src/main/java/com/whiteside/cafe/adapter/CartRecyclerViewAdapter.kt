package com.whiteside.cafe.adapter

import android.app.AlertDialog
import android.content.Context
import android.graphics.BitmapFactory
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.whiteside.cafe.databinding.CartItemCardViewBinding
import com.whiteside.cafe.model.Item
import com.whiteside.cafe.model.Product
import com.whiteside.cafe.ui.cart.CartFragment
import com.whiteside.cafe.ui.cart.CartPresenter
import javax.inject.Inject

class CartRecyclerViewAdapter(
    private val list: ArrayList<Item>,
    private val context: Context,
    private val cartFragment: CartFragment
) : RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder>() {

    @Inject
    lateinit var productPresenter: ProductPresenter

    @Inject
    lateinit var cartPresenter: CartPresenter

    private val fStore: FirebaseFirestore?
    private val auth: FirebaseAuth?
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CartItemCardViewBinding.inflate(LayoutInflater.from(parent.context))
//            LayoutInflater.from(parent.context).inflate(R.layout.cart_item_card_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        productPresenter.getProduct(list[position].categoryName, list[position].productId) {
            holder.setView(it)
        }
    }

    private fun updateView(product: Product) {

    }

    private fun setListener(holder: ViewHolder?, position: Int, product: Product) {
        setEditListener(holder, position, product.price)
        setRemoveListener(holder, product)
    }

    private fun setRemoveListener(holder: ViewHolder?, product: Product) {
        holder!!.remove!!.setOnClickListener {
            cartPresenter.removeItem(product) {}
        }
    }

    private fun setEditListener(holder: ViewHolder?, position: Int, price: Float) {
        holder!!.edit!!.setOnClickListener(View.OnClickListener {
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
            holder!!.quantity!!.text = quantity.toString()
//            holder.total_price!!.text = "${Integer.toString(quantity  * list!![position].)} EGP"
            cartPresenter.updateQuantity(list[position], quantity)
//            cartFragment.editTotalBill((quantity * price).toDouble())
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onRemoveCartItemSuccess() {
//        cartFragment.updateView()
    }

    inner class ViewHolder(val bind: CartItemCardViewBinding) : RecyclerView.ViewHolder(itemView) {
        fun setView(product: Product) {
            holder.imageView!!.setImageBitmap(
                BitmapFactory.decodeByteArray(
                    product.productPics[0].toBytes(), 0, product.productPics[0].toBytes().size
                )
            )
            bind.title.text = product.title
            val quantity = list[position].quantity
            bind.quantity.text = quantity.toString()
            val totalPrice = product.price * quantity!!
            bind.totalPrice.text = "$totalPrice EGP"
            cartFragment.editTotalBill(totalPrice.toDouble())
            setListener(holder, position, product)
        }
    }

    init {
        fStore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
    }
}