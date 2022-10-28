package com.englizya.home_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import com.englizya.home_screen.databinding.CardViewOfferBinding
import com.englizya.model.model.Offer
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso

class OfferSliderAdapter(
    private var offers: List<Offer>,
    private val onItemClicked: (Offer) -> Unit,

    ) : SliderViewAdapter<OfferSliderAdapter.Holder>() {

    inner class Holder(private val binding: CardViewOfferBinding) :
        SliderViewAdapter.ViewHolder(binding.root) {
        fun updateUI(offer: Offer) {
            //  binding.offerTitle.setText(offer.offerTitle)
            Picasso.get().load(offer.offerImageUrl).into(binding.offerImage)
            itemView.setOnClickListener{
                navigateToOfferDetails(offer)
            }
        }

        private fun navigateToOfferDetails(offer: Offer) {
            onItemClicked(offer)

        }

    }


    fun setOffers(list: List<Offer>) {
        offers = (list)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return offers.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): Holder {
        val binding = CardViewOfferBinding.inflate(
            LayoutInflater.from(parent?.context),
            parent,
            false
        )
        return Holder(binding = binding)
    }

    override fun onBindViewHolder(viewHolder: Holder?, position: Int) {
        viewHolder?.updateUI(offers[position])
//        viewHolder.
    }


}