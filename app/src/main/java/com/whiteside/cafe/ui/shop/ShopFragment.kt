package com.whiteside.cafe.ui.shop

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.firestore.FirebaseFirestore
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator
import com.whiteside.cafe.OffersPagerAdapter
import com.whiteside.cafe.R
import com.whiteside.cafe.model.Offer
import java.util.*

class ShopFragment : Fragment(), OnGetOffersListener {
    private val fStore: FirebaseFirestore?
    private var best_dots_indicator: SpringDotsIndicator? = null
    private var last_dots_indicator: SpringDotsIndicator? = null
    private var lastOffers: MutableList<Offer?>?
    private var bestOffers: MutableList<Offer?>?
    private var lastOffersPagerAdapter: OffersPagerAdapter? = null
    private var bestOffersPagerAdapter: OffersPagerAdapter? = null
    private var best_offers_pager: ViewPager2? = null
    private var last_offers_pager: ViewPager2? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_shop, container, false)
        last_offers_pager = root.findViewById(R.id.last_offers_pager)
        best_offers_pager = root.findViewById(R.id.best_offers_pager)
        best_dots_indicator = root.findViewById(R.id.best_dots_indicator)
        last_dots_indicator = root.findViewById(R.id.last_dots_indicator)
        lastOffersPagerAdapter = OffersPagerAdapter(lastOffers, activity)
        bestOffersPagerAdapter = OffersPagerAdapter(bestOffers, activity)
        last_offers_pager.setAdapter(lastOffersPagerAdapter)
        best_offers_pager.setAdapter(bestOffersPagerAdapter)
        best_dots_indicator.setViewPager2(best_offers_pager)
        last_dots_indicator.setViewPager2(last_offers_pager)
        val inflater1 = TransitionInflater.from(context)
        exitTransition = inflater1.inflateTransition(R.transition.fragment_in)
        enterTransition = inflater1.inflateTransition(R.transition.fragment_out)
        val presenter = OffersPresenter(this)
        presenter.getOffers()
        return root
    }

    private fun refreshBestOffers(offer: Offer?) {
        offer.type = 1
        bestOffers.add(offer)
        Collections.sort(bestOffers)
        if (bestOffers.size > 5) {
            bestOffers = bestOffers.subList(0, 6)
        }
        bestOffersPagerAdapter.notifyDataSetChanged()
        best_dots_indicator.setViewPager2(best_offers_pager)
    }

    private fun refreshLastOffers(offer: Offer?) {
        offer.type = 2
        lastOffers.add(offer)
        Collections.sort(lastOffers)
        if (lastOffers.size > 5) {
            lastOffers = lastOffers.subList(0, 6)
        }
        lastOffersPagerAdapter.notifyDataSetChanged()
        last_dots_indicator.setViewPager2(last_offers_pager)
    }

    override fun onGetOfferSuccess(offer: Offer?) {
        refreshLastOffers(offer)
        refreshBestOffers(offer)
    }

    override fun onGetOfferFailed(e: Exception?) {
        e?.printStackTrace()
    }

    init {
        fStore = FirebaseFirestore.getInstance()
        lastOffers = ArrayList<Any?>()
        bestOffers = ArrayList<Any?>()
    }
}