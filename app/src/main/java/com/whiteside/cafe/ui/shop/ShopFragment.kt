package com.whiteside.cafe.ui.shop

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.whiteside.cafe.OffersPagerAdapter
import com.whiteside.cafe.R
import com.whiteside.cafe.databinding.FragmentShopBinding
import com.whiteside.cafe.model.Offer
import com.whiteside.cafe.ui.offer.OfferPresenter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShopFragment : Fragment() {
    private var lastOffers: ArrayList<Offer> = ArrayList()
    private var bestOffers: ArrayList<Offer> = ArrayList()

    private lateinit var lastOffersPagerAdapter: OffersPagerAdapter
    private lateinit var bestOffersPagerAdapter: OffersPagerAdapter

    private lateinit var bind: FragmentShopBinding

    @Inject
    lateinit var offerPresenter: OfferPresenter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_shop, container, false)

        lastOffersPagerAdapter = OffersPagerAdapter(lastOffers, requireActivity())
        bestOffersPagerAdapter = OffersPagerAdapter(bestOffers, requireActivity())

        bind.lastOffersPager.adapter = lastOffersPagerAdapter
        bind.bestOffersPager.adapter = bestOffersPagerAdapter

        bind.bestDotsIndicator.setViewPager2(bind.bestOffersPager)
        bind.lastDotsIndicator.setViewPager2(bind.lastOffersPager)

        val inflater1 = TransitionInflater.from(context)
        exitTransition = inflater1.inflateTransition(R.transition.fragment_in)
        enterTransition = inflater1.inflateTransition(R.transition.fragment_out)

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