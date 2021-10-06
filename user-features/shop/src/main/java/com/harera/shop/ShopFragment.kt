package com.harera.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.harera.categories.CategoriesAdapter
import com.harera.model.modelget.Category
import com.harera.model.modelget.Offer
import com.harera.model.modelget.Product
import com.harera.common.base.BaseFragment
import com.harera.shop.databinding.FragmentShopBinding

class ShopFragment : BaseFragment() {

    private val shopViewModel: ShopViewModel by viewModels()
    private lateinit var bind: FragmentShopBinding

    private lateinit var offersAdapter: OffersAdapter
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        bind = FragmentShopBinding.inflate(layoutInflater)
        offersAdapter = OffersAdapter(ArrayList(), findNavController())
        productsAdapter = ProductsAdapter(ArrayList(), findNavController())
        categoriesAdapter = CategoriesAdapter(ArrayList(), findNavController())
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOffersAdapter()
        setupProductsAdapter()
        setupCategoriesAdapter()
        setupObserves()

        shopViewModel.getOffers()
        shopViewModel.getProducts()
        shopViewModel.getCategories()
    }

    private fun setupOffersAdapter() {
        bind.offers.adapter = offersAdapter
        bind.offers.clipToPadding = false
        bind.offers.clipChildren = false
        bind.offers.offscreenPageLimit = 3
        bind.offers.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    private fun setupCategoriesAdapter() {
        bind.categories.adapter = categoriesAdapter
        bind.categories.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupProductsAdapter() {
        bind.products.adapter = productsAdapter
        bind.products.layoutManager = GridLayoutManager(context, 2)
    }

    private fun setupObserves() {
        shopViewModel.offers.observe(viewLifecycleOwner) {
            updateOffers(it)
        }

        shopViewModel.products.observe(viewLifecycleOwner) {
            updateProducts(it)
        }

        shopViewModel.categories.observe(viewLifecycleOwner) {
            updateCategories(it)
        }
    }

    private fun updateCategories(categories: List<Category>) {
        categoriesAdapter.setCategories(categories)
    }

    private fun updateOffers(offers: List<Offer>) {
        offersAdapter.setOffers(offers)
    }

    private fun updateProducts(products: List<Product>) {
        productsAdapter.setProducts(products)
    }
}
