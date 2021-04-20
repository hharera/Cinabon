package com.whiteside.cafe.ui.offer

import android.app.Application
import com.whiteside.cafe.api.firebase.FirebaseOfferRepository
import com.whiteside.cafe.model.Offer
import javax.inject.Inject

class OfferPresenter @Inject constructor(
    val application: Application,
    val repo: FirebaseOfferRepository
) {

    fun getOfferById(offerId: String, result: (Offer) -> (Unit)) {
        repo.getOfferById(offerId)
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
}