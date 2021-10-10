package com.harera.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.harera.common.base.BaseFragment
import com.harera.model.modelget.WishItem
import com.harera.wish_item.WishListAdapter
import com.harera.wishlist.databinding.FragmentWishlistBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishListFragment : BaseFragment() {

    private lateinit var bind: FragmentWishlistBinding
    private val wishListViewModel: WishListViewModel by viewModels()
    private lateinit var wishListAdapter: WishListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        bind = FragmentWishlistBinding.inflate(inflater, container, false)

        wishListAdapter = WishListAdapter(
            onAddToCartClicked = {
                wishListViewModel.addWishItemToCart(it)
            },
            onRemoveItemClicked = {
                wishListViewModel.removeWishItem(it)
            }
        )
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()

        wishListViewModel.wishList.observe(viewLifecycleOwner) { map ->
            updateWishList(map.map {
                it.value
            })
        }

        wishListViewModel.getWishListItems()
    }

    private fun setupAdapter() {
        bind.wishlist.setHasFixedSize(true)
        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        bind.wishlist.addItemDecoration(itemDecorator)
        bind.wishlist.adapter = wishListAdapter
    }

    private fun updateWishList(list: List<WishItem>) {
        wishListAdapter.updateWishList(list)
        wishListAdapter.notifyDataSetChanged()
    }
}