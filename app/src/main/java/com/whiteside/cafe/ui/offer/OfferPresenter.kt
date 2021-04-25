package com.whiteside.cafe.ui.offer

import com.whiteside.cafe.api.firebase.FirebaseOfferRepository
import com.whiteside.cafe.model.Offer
import javax.inject.Inject

class OfferPresenter @Inject constructor(
    val repo: FirebaseOfferRepository
) {
    fun getBestOffer(offerId: String, result: (Offer) -> (Unit)) {
        repo.getBestOffer(offerId)
            .addOnSuccessListener {
                val offer = it.toObject(Offer::class.java)!!
                result(offer)
            }
    }

    fun getLastOffer(offerId: String, result: (Offer) -> (Unit)) {
        repo.getLastOffer(offerId)
            .addOnSuccessListener {
                val offer = it.toObject(Offer::class.java)!!
                result(offer)
            }
    }

    fun getBestOffers(result: (Offer) -> Unit) {
        repo.getBestOffers()
            .addOnSuccessListener {
                it.documents.forEach {
                    val offer = it.toObject(Offer::class.java)!!
                    result(offer)
                }
            }
    }

    fun getLastOffers(result: (Offer) -> Unit) {
        repo.getLastOffers()
            .addOnSuccessListener {
                it.documents.forEach {
                    val offer = it.toObject(Offer::class.java)!!
                    result(offer)
                }
            }
    }

    fun setBestOffer(offer: Offer, result: (Unit) -> Unit) {
        repo.setBestOffer(offer)
            .addOnSuccessListener {
                result
            }
    }

    fun setLastOffer(offer: Offer, result: (Unit) -> Unit) {
        repo.setLastOffer(offer)
            .addOnSuccessListener {
                result
            }
    }
}