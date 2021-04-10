package com.whiteside.cafe.ui.wishList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.whiteside.cafe.R
import com.whiteside.cafe.adapter.WishListRecyclerViewAdapter
import com.whiteside.cafe.databinding.FragmentEmptyListBinding
import com.whiteside.cafe.databinding.FragmentWishlistBinding
import com.whiteside.cafe.model.Item
import java.util.*

class WishListFragment : Fragment(), OnGetWishListItem {
    private var wishListItems: MutableList<Item> = ArrayList<Item>()

    private lateinit var adapter: WishListRecyclerViewAdapter
    private lateinit var wishListPresenter: WishListPresenter

    private lateinit var emptyListBinding: FragmentEmptyListBinding
    private lateinit var bind: FragmentWishlistBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_wishlist, container, false)

        adapter = WishListRecyclerViewAdapter(wishListItems, requireContext(), this)
        bind.cart.adapter = adapter

        wishListPresenter = WishListPresenter()
        wishListPresenter.onGetWishListItem = (this)
        wishListPresenter.getWishListItems()

        return bind.root
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
        bind.fragmentEmptyList.root.visibility = View.VISIBLE

        bind.fragmentEmptyList.cartShopping.setOnClickListener {
            findNavController().navigate(R.id.navigation_shop)
        }
    }
}