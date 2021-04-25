package com.whiteside.cafe.ui.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.whiteside.cafe.R
import com.whiteside.cafe.adapter.WishListRecyclerViewAdapter
import com.whiteside.cafe.databinding.FragmentWishlistBinding
import com.whiteside.cafe.model.Item
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WishListFragment : Fragment() {
    private lateinit var bind: FragmentWishlistBinding
    private var wishListItems: ArrayList<Item> = ArrayList()

    @Inject
    lateinit var wishListPresenter: WishListPresenter

    @Inject
    lateinit var adapter: WishListRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        bind = FragmentWishlistBinding.inflate(inflater, container, false)
        bind.cart.adapter = adapter
        adapter.list = wishListItems

        bind.fragmentEmptyList.cartShopping.setOnClickListener {
            findNavController().navigate(R.id.navigation_shop)
        }

        getWishList()

        return bind.root
    }

    fun getWishList() {
        wishListPresenter.getWishList {
            wishListItems.add(it)
            adapter.notifyDataSetChanged()
            updateView()
        }
    }

    private fun updateView() {
        bind.cart.visibility = View.VISIBLE
        bind.fragmentEmptyList.root.visibility = View.INVISIBLE
    }
}