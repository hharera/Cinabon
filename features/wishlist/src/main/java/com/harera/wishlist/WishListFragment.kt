package com.harera.wishlist

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.harera.common.base.BaseFragment
import com.harera.common.navigation.utils.Arguments
import com.harera.common.navigation.utils.Destinations
import com.harera.common.navigation.utils.NavigationUtils
import com.harera.repository.domain.WishItem
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
            onItemClicked = { productId ->
                viewProduct(productId)
            },
            onRemoveItemClicked = {
                wishListViewModel.removeWishItem(it)
            },

            )
        return bind.root
    }

    private fun viewProduct(productId: String) {
        findNavController().navigate(
            Uri.parse(
                NavigationUtils.getUriNavigation(
                    Arguments.HYPER_PANDA_DOMAIN,
                    Destinations.PRODUCT,
                    productId
                )
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupListeners()

        wishListViewModel.wishList.observe(viewLifecycleOwner) { map ->
            updateWishList(map.map {
                it.value
            })
        }

        wishListViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }

        wishListViewModel.exception.observe(viewLifecycleOwner) {
            handleError(it)
        }

        wishListViewModel.getWishListItems()
    }

    private fun setupListeners() {
//        bind.emptyList.cartShopping.setOnClickListener {
//            goShop()
//        }
    }

    private fun goShop() {
        findNavController().navigate(
            Uri.parse(
                NavigationUtils.getUriNavigation(
                    Arguments.HYPER_PANDA_DOMAIN,
                    Destinations.SHOP,
                    null
                )
            )
        )
    }

    private fun setupAdapter() {
        bind.wishlist.setHasFixedSize(true)
        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        bind.wishlist.addItemDecoration(itemDecorator)
        bind.wishlist.adapter = wishListAdapter
    }

    private fun updateWishList(list: List<WishItem>) {
//        if (list.isNotEmpty()) {
//            bind.emptyList.root.visibility = View.INVISIBLE
//            bind.wishlist.visibility = View.VISIBLE
//            wishListAdapter.updateWishList(list)
//            wishListAdapter.notifyDataSetChanged()
//        } else {
//            bind.emptyList.root.visibility = View.VISIBLE
//            bind.wishlist.visibility = View.INVISIBLE
//        }
    }
}