package com.whiteside.cafe.ui.wishList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.whiteside.cafe.R
import com.whiteside.cafe.adapter.WishListRecyclerViewAdapter
import com.whiteside.cafe.model.Item
import java.util.*

class WishListFragment : Fragment(), OnGetWishListItem {
    private val auth: FirebaseAuth?
    private val fStore: FirebaseFirestore?
    private var wishListItems: MutableList<Item>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WishListRecyclerViewAdapter
    private lateinit var emptyWishList: LinearLayout
    private lateinit var shopping: LinearLayout
    private lateinit var root: View
    private lateinit var wishListPresenter: WishListPresenter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_wishlist, container, false)

        emptyWishList = root.findViewById(R.id.empty_wish_list)
        shopping = root.findViewById(R.id.cart_shopping)
        recyclerView = root.findViewById(R.id.wish_list)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        adapter = WishListRecyclerViewAdapter(wishListItems, requireContext(), this)
        recyclerView.adapter = adapter
        wishListPresenter = WishListPresenter()
        wishListPresenter.onGetWishListItem = (this)
        wishListPresenter.getWishListItems()
        return root
    }

    private fun setEmptyView() {
        recyclerView.visibility = View.INVISIBLE
        emptyWishList.visibility = View.VISIBLE
        shopping.setOnClickListener {
            findNavController().navigate(R.id.wishlist_shop_action)
        }
    }

    fun updateView() {
        wishListItems.clear()
        adapter.notifyDataSetChanged()
        wishListPresenter.getWishListItems()
    }

    override fun onGetWishListItemSuccess(item: Item) {
        wishListItems.add(item)
        adapter.notifyDataSetChanged()
    }

    override fun onGetWishListItemFailed(e: Exception) {
        e.printStackTrace()
    }

    override fun onWishListIsEmpty() {
        setEmptyView()
    }

    init {
        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        wishListItems = ArrayList<Item>()
    }
}