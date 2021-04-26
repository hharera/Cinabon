package com.whiteside.cafe.ui.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.whiteside.cafe.R
import com.whiteside.cafe.adapter.CartRecyclerViewAdapter
import com.whiteside.cafe.api.repository.AuthManager
import com.whiteside.cafe.databinding.CartCheckoutBinding
import com.whiteside.cafe.databinding.FragmentCartBinding
import com.whiteside.cafe.model.Item
import com.whiteside.cafe.ui.receipt.Receipt
import com.whiteside.cafe.ui.signUp.SignUp
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : Fragment() {
    private var items: ArrayList<Item> = ArrayList<Item>()

    @Inject
    lateinit var adapter: CartRecyclerViewAdapter

    @Inject
    lateinit var presenter: CartPresenter

    @Inject
    lateinit var authManager: AuthManager

    private lateinit var bind: FragmentCartBinding
    private lateinit var checkoutBinding: CartCheckoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        bind = FragmentCartBinding.inflate(inflater, container, false)

        checkoutBinding = bind.checkout
        bind.cart.adapter = adapter
        bind.cart.setHasFixedSize(true)
        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        bind.cart.addItemDecoration(itemDecorator)

        adapter.list = (items)

        getCart()
        setupListener()

        return bind.root
    }

    private fun getCart() {
        presenter.getCartItems {
            onGetCartItem(it)
        }
    }

    private fun setupListener() {
        checkoutBinding.checkOut.setOnClickListener {
            if (authManager.getCurrentUser()!!.isAnonymous) {
                val intent = Intent(context, SignUp::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(context, Receipt::class.java)
//                intent.putExtra("Total Bill", totalBill)
                startActivity(intent)
            }
        }
    }

    fun updateView() {
        bind.fragmentEmptyList.root.visibility = View.INVISIBLE
        bind.checkoutLayout.visibility = View.VISIBLE
        bind.cart.visibility = View.VISIBLE

        bind.fragmentEmptyList.cartShopping.setOnClickListener {
            findNavController().navigate(R.id.navigation_shop)
        }
    }

    private fun onGetCartItem(item: Item) {
        updateView()
        items.add(item)
        adapter.notifyDataSetChanged()
    }

}