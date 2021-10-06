package com.harera.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.harera.common.utils.navigation.Arguments.PRODUCT_ID
import com.harera.common.base.BaseFragment
import com.harera.model.modelget.Product
import com.harera.common.utils.Status
import com.harera.image_slider.ProductPicturesAdapter
import com.harera.product.databinding.FragmentProductViewBinding

class ProductFragment : BaseFragment() {
    private val productViewModel: ProductViewModel by viewModels()
    private lateinit var bind: FragmentProductViewBinding
    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            val productId = it.getString(PRODUCT_ID)!!
            productViewModel.setProductId(productId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        bind = FragmentProductViewBinding.inflate(layoutInflater)
        productsAdapter = ProductsAdapter(navController = findNavController(), products = emptyList())
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productViewModel.getWishState()
        productViewModel.getCartState()
        setupProductsAdapter()

        productViewModel.getProduct().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.ERROR -> {
                    handleError(it.error)
                }
                Status.SUCCESS -> {
                    handleSuccess()
                    updateUI(it.data!!)
                    productViewModel.getCategoryProducts(it.data!!.categoryName)
                }
                Status.LOADING -> {
                    handleLoading()
                }
            }
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
    }

    private fun updateProducts(products: List<Product>) {
        productsAdapter.setProducts(products = products)
    }

    private fun setupProductsAdapter() {
        bind.products.adapter = productsAdapter
        bind.products.layoutManager = GridLayoutManager(context, 2)
    }

    private fun updateWishIcon(state: Boolean) {
        if(state)
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
            productViewModel.changeWishState()
        }
    }
}