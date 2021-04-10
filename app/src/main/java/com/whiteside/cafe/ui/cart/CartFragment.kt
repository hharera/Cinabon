package com.whiteside.cafe.ui.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.whiteside.cafe.R
import com.whiteside.cafe.Receipt
import com.whiteside.cafe.adapter.CartRecyclerViewAdapter
import com.whiteside.cafe.databinding.CartCheckoutBinding
import com.whiteside.cafe.databinding.FragmentCartBinding
import com.whiteside.cafe.model.Item
import com.whiteside.cafe.ui.signUp.SignUp
import java.util.*

class CartFragment : Fragment(), OnGetCartItem {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private var items: ArrayList<Item> = ArrayList<Item>()
    private lateinit var adapter: CartRecyclerViewAdapter
    private var totalBill: Double
    private lateinit var presenter: CartPresenter
    private lateinit var recyclerView: RecyclerView

    private lateinit var bind: FragmentCartBinding
    private lateinit var checkoutBinding: CartCheckoutBinding

    private lateinit var viewGroup: ViewGroup

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        checkoutBinding = bind.checkout

        viewGroup = container!!

        recyclerView = bind.cart
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        adapter = CartRecyclerViewAdapter(items, requireContext(), this)
        recyclerView.adapter = adapter

        presenter = CartPresenter()
        presenter.onGetCartItem = (this)
        presenter.getCartItems()
        setCheckOutListener()

        return bind.root
    }

    private fun setCheckOutListener() {
        checkoutBinding.checkOut.setOnClickListener {
            if (auth.currentUser!!.isAnonymous) {
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
        checkoutBinding.billCost.text = ("$totalBill EGP")
    }

    override fun onGetCartItemSuccess(item: Item) {
        items.add(item)
        adapter.notifyDataSetChanged()
    }

    override fun onGetCartItemFailed(e: Exception) {
    }

    override fun onCartIsEmpty() {
        bind.fragmentEmptyList.root.visibility = View.VISIBLE
        bind.checkoutLayout.visibility = View.INVISIBLE

        bind.fragmentEmptyList.cartShopping.setOnClickListener {
            findNavController().navigate(R.id.navigation_shop)
        }
    }

    init {
        totalBill = 0.0
    }
}