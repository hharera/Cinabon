package com.whiteside.cafe.ui.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.whiteside.cafe.CafeApp
import com.whiteside.cafe.R
import com.whiteside.cafe.adapter.WishListRecyclerViewAdapter
import com.whiteside.cafe.databinding.FragmentWishlistBinding
import com.whiteside.cafe.model.Item
import javax.inject.Inject

class WishListFragment : Fragment() {
    private var wishListItems: ArrayList<Item> = ArrayList()
    private lateinit var adapter: WishListRecyclerViewAdapter

    @Inject
    lateinit var wishListPresenter: WishListPresenter

    private lateinit var bind: FragmentWishlistBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_wishlist, container, false)

        adapter = WishListRecyclerViewAdapter(
            wishListItems,
            requireContext(),
            requireActivity().application as CafeApp,
            this
        )

        bind.cart.adapter = adapter

        bind.fragmentEmptyList.cartShopping.setOnClickListener {
            findNavController().navigate(R.id.navigation_shop)
        }

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