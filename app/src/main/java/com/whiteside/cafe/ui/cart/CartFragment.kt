package com.whiteside.cafe.ui.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.whiteside.cafe.R
import com.whiteside.cafe.Receipt
import com.whiteside.cafe.adapter.CartRecyclerViewAdapter
import com.whiteside.cafe.databinding.FragmentCartBinding
import com.whiteside.cafe.model.Item
import com.whiteside.cafe.ui.signUp.SignUp
import java.util.*

class CartFragment : Fragment(), OnGetCartItem {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var fStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var items: ArrayList<Item> = ArrayList<Item>()
    private lateinit var adapter: CartRecyclerViewAdapter
    private lateinit var emptyCart: LinearLayout
    private lateinit var shopping: LinearLayout
    private var totalBill: Double
    private lateinit var presenter: CartPresenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var totalBillView: TextView

    private lateinit var bind: FragmentCartBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)

        bind.cartShopping.setOnClickListener {
            findNavController().navigate(R.id.cart_shop_action)
        }

        recyclerView = bind.cart
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        adapter = CartRecyclerViewAdapter(items, requireContext(), this)
        recyclerView.adapter = adapter
        emptyCart = bind.emptyCart
        shopping = bind.cartShopping
        totalBillView = bind.totalBill

        presenter = CartPresenter()
        presenter.onGetCartItem = (this)
        presenter.getCartItems()
        setCheckOutListener()

        return bind.root
    }

    private fun setCheckOutListener() {
        bind.checkOut.setOnClickListener {
            if (auth.currentUser.isAnonymous) {
                val intent = Intent(context, SignUp::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(context, Receipt::class.java)
                intent.putExtra("Total Bill", totalBill)
                startActivity(intent)
            }
        }
    }

    fun updateView() {
        items.clear()
        adapter.notifyDataSetChanged()
        totalBill = 0.0
        presenter.getCartItems()
    }

    fun editTotalBill(price: Double) {
        totalBill += price
        totalBillView.text = ("$totalBill EGP")
    }

    private fun getEmptyCartView() {
        emptyCart.visibility = View.VISIBLE
    }

    override fun onGetCartItemSuccess(item: Item) {
        items.add(item)
        adapter.notifyDataSetChanged()
        bind.checkOutLayout.visibility = View.VISIBLE
    }

    override fun onGetCartItemFailed(e: Exception) {}

    override fun onCartIsEmpty() {
        bind.checkOutLayout.visibility = View.INVISIBLE
        getEmptyCartView()
    }

    init {
        totalBill = 0.0
    }
}