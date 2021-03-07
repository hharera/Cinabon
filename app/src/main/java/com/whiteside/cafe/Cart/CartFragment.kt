package com.whiteside.cafe.Cart

import Model.Item
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.whiteside.cafe.HomeActivity
import com.whiteside.cafe.R
import com.whiteside.cafe.Receipt
import com.whiteside.cafe.SignUp.SignUp
import java.util.*

class CartFragment : Fragment(), OnGetCartItem {
    private val auth: FirebaseAuth?
    private val fStore: FirebaseFirestore?
    private val items: ArrayList<*>?
    private var adapter: CartRecyclerViewAdapter? = null
    private var emptyCart: LinearLayout? = null
    private var shopping: LinearLayout? = null
    private var check_out: View? = null
    private var totalBill: Double
    private var presenter: CartPresenter? = null
    private var recyclerView: RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_cart, container, false)
        recyclerView = root.findViewById(R.id.cart)
        recyclerView.setLayoutManager(LinearLayoutManager(context))
        recyclerView.setHasFixedSize(true)
        adapter = CartRecyclerViewAdapter(items, context, this)
        recyclerView.setAdapter(adapter)
        emptyCart = root.findViewById(R.id.empty_cart)
        shopping = root.findViewById(R.id.cart_shopping)
        check_out = root.findViewById(R.id.check_out)
        totalBillView = root.findViewById(R.id.bill_cost)
        presenter = CartPresenter()
        presenter.setOnGetCartItem(this)
        presenter.getCartItems()
        setCheckOutListener()
        return root
    }

    private fun setCheckOutListener() {
        check_out.setOnClickListener(View.OnClickListener {
            if (auth.getCurrentUser().isAnonymous) {
                val intent = Intent(context, SignUp::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(context, Receipt::class.java)
                intent.putExtra("Total Bill", totalBill)
                startActivity(intent)
            }
        })
    }

    fun updateView() {
        items.clear()
        adapter.notifyDataSetChanged()
        totalBill = 0.0
        presenter.getCartItems()
    }

    fun editTotalBill(price: Double) {
        totalBill += price
        totalBillView.setText("$totalBill EGP")
    }

    private fun getEmptyCartView() {
        emptyCart.setVisibility(View.VISIBLE)
        shopping.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            activity.startActivity(intent)
            activity.finish()
        })
    }

    override fun onGetCartItemSuccess(item: Item?) {
        items.add(item)
        adapter.notifyDataSetChanged()
        check_out.setVisibility(View.VISIBLE)
    }

    override fun onGetCartItemFailed(e: Exception?) {}
    override fun onCartIsEmpty() {
        check_out.setVisibility(View.INVISIBLE)
        getEmptyCartView()
    }

    companion object {
        private var totalBillView: TextView? = null
    }

    init {
        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        items = ArrayList<Any?>()
        totalBill = 0.0
    }
}