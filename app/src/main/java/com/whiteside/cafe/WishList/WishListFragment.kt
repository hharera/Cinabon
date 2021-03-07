package com.whiteside.cafe.WishList

import Model.Item
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.whiteside.cafe.HomeActivity
import com.whiteside.cafe.R
import java.util.*

class WishListFragment : Fragment(), OnGetWishListItem {
    private val auth: FirebaseAuth?
    private val fStore: FirebaseFirestore?
    private val wishListItems: MutableList<Item?>?
    private var recyclerView: RecyclerView? = null
    private var adapter: WishListRecyclerViewAdapter? = null
    private var empty_wish_list: LinearLayout? = null
    private var shopping: LinearLayout? = null
    private var root: View? = null
    private var wishListPresenter: WishListPresenter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_wishlist, container, false)
        empty_wish_list = root.findViewById(R.id.empty_wish_list)
        shopping = root.findViewById(R.id.cart_shopping)
        recyclerView = root.findViewById(R.id.wish_list)
        recyclerView.setLayoutManager(LinearLayoutManager(context))
        recyclerView.setHasFixedSize(true)
        adapter = WishListRecyclerViewAdapter(wishListItems, context, this)
        recyclerView.setAdapter(adapter)
        wishListPresenter = WishListPresenter()
        wishListPresenter.setOnGetWishListItem(this)
        wishListPresenter.getWishListItems()
        return root
    }

    private fun setEmptyView() {
        recyclerView.setVisibility(View.INVISIBLE)
        empty_wish_list.setVisibility(View.VISIBLE)
        shopping.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            activity.startActivity(intent)
            activity.finish()
        })
    }

    fun updateView() {
        wishListItems.clear()
        adapter.notifyDataSetChanged()
        wishListPresenter.getWishListItems()
    }

    override fun onGetWishListItemSuccess(item: Item?) {
        wishListItems.add(item)
        adapter.notifyDataSetChanged()
    }

    override fun onGetWishListItemFailed(e: Exception?) {
        e?.printStackTrace()
    }

    override fun onWishListIsEmpty() {
        setEmptyView()
    }

    init {
        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        wishListItems = ArrayList<Any?>()
    }
}