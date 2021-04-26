package com.whiteside.cafe.ui.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.whiteside.cafe.adapter.OffersPagerAdapter
import com.whiteside.cafe.databinding.FragmentShopBinding
import com.whiteside.cafe.model.Offer
import com.whiteside.cafe.ui.offer.OfferPresenter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShopFragment : Fragment() {
    private var lastOffers: ArrayList<Offer> = ArrayList()
    private var bestOffers: ArrayList<Offer> = ArrayList()

    private var lastOffersPagerAdapter= OffersPagerAdapter(lastOffers, "LastOffers")
    private var bestOffersPagerAdapter= OffersPagerAdapter(bestOffers, "BestOffers")

    private lateinit var bind: FragmentShopBinding

    @Inject
    lateinit var offerPresenter: OfferPresenter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        bind = FragmentShopBinding.inflate(layoutInflater)

        bind.lastOffersPager.adapter = lastOffersPagerAdapter
        bind.bestOffersPager.adapter = bestOffersPagerAdapter

        bind.bestDotsIndicator.setViewPager2(bind.bestOffersPager)
        bind.lastDotsIndicator.setViewPager2(bind.lastOffersPager)

        offerPresenter.getLastOffers {
            refreshLastOffers(it)
        }

        offerPresenter.getBestOffers {
            refreshBestOffers(it)
        }
        return bind.root
    }

    private fun refreshBestOffers(offer: Offer) {
        bestOffers.add(offer)
        bestOffersPagerAdapter.notifyDataSetChanged()
        bind.bestDotsIndicator.setViewPager2(bind.bestOffersPager)
    }

    private fun refreshLastOffers(offer: Offer) {
        lastOffers.add(offer)
        lastOffersPagerAdapter.notifyDataSetChanged()
        bind.lastDotsIndicator.setViewPager2(bind.lastOffersPager)
    }

}