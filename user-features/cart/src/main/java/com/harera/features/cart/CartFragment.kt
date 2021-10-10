package com.harera.features.cart

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.harera.cart_item.CartAdapter
import com.harera.common.base.BaseFragment
import com.harera.common.utils.navigation.Arguments
import com.harera.common.utils.navigation.Destinations
import com.harera.common.utils.navigation.NavigationUtils
import com.harera.features.cart.databinding.FragmentCartBinding
import com.harera.model.modelget.CartItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment() {

    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter
    private lateinit var bind: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        bind = FragmentCartBinding.inflate(layoutInflater, container, false)
        setupCartAdapter()

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartViewModel.getCartItems()

        cartViewModel.cartList.observe(viewLifecycleOwner) {
            updateCartList(it.map { it.value })
        }
    }

    private fun updateCartList(cartList: List<CartItem>) {
        cartAdapter.setCartList(cartList)
    }

    private fun setupCartAdapter() {
        cartAdapter = CartAdapter(
            emptyList(),
            onRemoveItemClicked = {
                cartViewModel.removeItem(it)
            },
            onItemClicked = { productId ->
                findNavController().navigate(
                    Uri.parse(
                        NavigationUtils.getUriNavigation(
                            Arguments.HYPER_PANDA_DOMAIN,
                            Destinations.PRODUCT,
                            productId
                        )
                    )
                )
            },
            onMoveToFavouriteClicked = {
                cartViewModel.moveToFavourite(it)
            },
            onQuantityMinusClicked = {
                cartViewModel.minusQuantity(it)
            },
            onQuantityPlusClicked = {
                cartViewModel.plusQuantity(it)
            }
        )
        bind.carts.setHasFixedSize(true)
        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        bind.carts.addItemDecoration(itemDecorator)
        bind.carts.adapter = cartAdapter
    }
}