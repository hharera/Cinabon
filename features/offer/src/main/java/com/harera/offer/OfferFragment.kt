package com.harera.offer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.harera.common.base.BaseFragment
import com.harera.common.navigation.utils.Arguments.OFFER_ID
import com.harera.offer.databinding.FragmentOfferViewBinding
import com.harera.repository.domain.Offer
import com.harera.repository.domain.Product
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfferFragment : BaseFragment() {
    private val offerViewModel: OfferViewModel by viewModels()
    private lateinit var bind: FragmentOfferViewBinding
    private lateinit var offersAdapter: OffersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            val offerId = it.getString(OFFER_ID)!!
            offerViewModel.setOfferId(offerId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        bind = FragmentOfferViewBinding.inflate(layoutInflater)
        offersAdapter = OffersAdapter(offers = emptyList())
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        offerViewModel.getWishState()
        offerViewModel.getCartState()
        offerViewModel.getOffer()
        offerViewModel.getRelatedOffers()
        setupProductsAdapter()

        offerViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading()
        }

        offerViewModel.product.observe(viewLifecycleOwner) {
            updateUI(it)
        }

        offerViewModel.exception.observe(viewLifecycleOwner) {
            handleError(it)
        }

        offerViewModel.wishState.observe(viewLifecycleOwner) {
            updateWishIcon(state = it)
            handleSuccess()
        }
        offerViewModel.offers.observe(viewLifecycleOwner) {
            updateProducts(it)
        }
    }

    private fun updateProducts(products: List<Offer>) {
        offersAdapter.setOffers(offers = products)
    }

    private fun setupProductsAdapter() {
        bind.products.adapter = offersAdapter
        bind.products.layoutManager = GridLayoutManager(context, 2)
    }

    private fun updateWishIcon(state: Boolean) {
        if (state)
            bind.wish.setImageResource(R.drawable.wished)
        else
            bind.wish.setImageResource(R.drawable.baseline_favorite_border_white_24dp)
    }

    private fun updateUI(product: Product) {
        bind.title.text = product.title
        bind.price.text = "${product.price} EGP"

        setupListener()
    }

    private fun setupListener() {
        bind.cart.setOnClickListener {
            offerViewModel.changeCartState()
        }

        bind.wish.setOnClickListener {
            offerViewModel.changeWishState()
        }
    }
}