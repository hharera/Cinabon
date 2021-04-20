package com.whiteside.cafe.ui.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.whiteside.cafe.CafeApp
import com.whiteside.cafe.R
import com.whiteside.cafe.Receipt
import com.whiteside.cafe.adapter.CartRecyclerViewAdapter
import com.whiteside.cafe.databinding.CartCheckoutBinding
import com.whiteside.cafe.databinding.FragmentCartBinding
import com.whiteside.cafe.model.Item
import com.whiteside.cafe.ui.signUp.SignUp
import com.whiteside.cafe.view_model.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : Fragment() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private var items: ArrayList<Item> = ArrayList<Item>()
    private var adapter: CartRecyclerViewAdapter
    private var totalBill: Double

    @Inject
    lateinit var presenter: CartPresenter

    private lateinit var bind: FragmentCartBinding
    private lateinit var cartViewModel: CartViewModel

    private lateinit var checkoutBinding: CartCheckoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        bind.viewModel = cartViewModel

        checkoutBinding = bind.checkout

        bind.cart.setHasFixedSize(true)
        bind.cart.adapter = adapter

        cartViewModel.getCartItems()

        getCart()

        setCheckOutListener()

        return bind.root
    }

    private fun getCart() {
        presenter.getCartItems(
            { onGetCartItem(it) },
            { onCartIsEmpty() }
        )
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
//        presenter.getCartItems()
    }

    fun editTotalBill(price: Double) {
        totalBill += price
        checkoutBinding.billCost.text = ("$totalBill EGP")
    }

    private fun onGetCartItem(item: Item) {
        items.add(item)
        adapter.notifyDataSetChanged()
    }

    private fun onCartIsEmpty() {
        bind.fragmentEmptyList.root.visibility = View.VISIBLE
        bind.checkoutLayout.visibility = View.INVISIBLE

        bind.fragmentEmptyList.cartShopping.setOnClickListener {
            findNavController().navigate(R.id.navigation_shop)
        }
    }

    init {
        adapter = CartRecyclerViewAdapter(
            items,
            requireContext(),
            requireActivity().application as CafeApp,
            this
        )
        totalBill = 0.0
    }
}