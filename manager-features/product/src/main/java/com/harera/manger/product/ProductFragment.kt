package com.harera.manger.product

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.harera.common.base.BaseFragment
import com.harera.common.navigation.utils.Arguments
import com.harera.common.navigation.utils.Destinations
import com.harera.common.navigation.utils.NavigationUtils
import com.harera.manger.product.databinding.FragmentProductBinding
import com.harera.repository.domain.Product
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProductFragment : BaseFragment() {

    private val productViewModel: ProductViewModel by viewModels()
    private lateinit var bind: FragmentProductBinding
    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        bind = FragmentProductBinding.inflate(layoutInflater)
        productsAdapter = ProductsAdapter(
            onProductClicked = {
                //TODO navigate to product
            }
        )

        arguments?.let {
            val productId = it.getString(Arguments.PRODUCT_ID)!!
            productViewModel.setProductId(productId)
        }


        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            productViewModel.checkWishState()
        }
        productViewModel.getCartState()
        setupProductsAdapter()

        productViewModel.getProduct().observe(viewLifecycleOwner) {
//            when (it.status) {
//                Status.ERROR -> {
//                    handleError(it.error)
//                }
//                Status.SUCCESS -> {
//                    handleSuccess()
//                    updateUI(it.data!!)
//                    productViewModel.getCategoryProducts(it.data!!.categoryName)
//                }
//                Status.LOADING -> {
//                    handleLoading()
//                }
//            }
        }

        productViewModel.loadingState.observe(viewLifecycleOwner) {
            handleLoading()
        }

        productViewModel.exception.observe(viewLifecycleOwner) {
            handleError(it)
        }

        productViewModel.wishState.observe(viewLifecycleOwner) {
            updateWishIcon(state = it)
            handleSuccess()
        }

        productViewModel.products.observe(viewLifecycleOwner) {
            updateProducts(it)
        }

        connectionLiveData.observe(viewLifecycleOwner) {
            productViewModel.updateConnectivity(it)
        }
    }

    private fun updateProducts(products: List<Product>) {
        productsAdapter.setProducts(productList = products)
    }

    private fun setupProductsAdapter() {
        bind.products.adapter = productsAdapter
        bind.products.layoutManager = GridLayoutManager(context, 2)
    }

    private fun updateWishIcon(state: Boolean) {
        if (state)
            bind.wish.setImageResource(R.drawable.wished)
        else
            bind.wish.setImageResource(R.drawable.wish)
    }

    private fun updateUI(product: Product) {
        bind.title.text = product.title
        bind.price.text = "${product.price} EGP"
        bind.productPics.adapter = ProductPicturesAdapter(product.productPictureUrls)

        setupListener()
    }

    private fun setupListener() {
        bind.cart.setOnClickListener {
            productViewModel.changeCartState()
        }

        bind.wish.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                productViewModel.changeWishState()
            }
        }

        bind.edit.setOnClickListener {
            findNavController().navigate(
                Uri.parse(
                    NavigationUtils.getUriNavigation(
                        Arguments.HYPER_PANDA_MANAGER_DOMAIN,
                        Destinations.EDIT_PRODUCT,
                        productViewModel.productId.value!!
                    )
                )
            )
        }
    }
}