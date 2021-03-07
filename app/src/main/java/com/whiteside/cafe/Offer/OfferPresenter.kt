package com.whiteside.cafe.Offer

import Model.Offer
import com.google.firebase.firestore.FirebaseFirestore
import com.whiteside.cafe.Shop.OnGetOffersListener

class OfferPresenter(private val listener: OnGetOffersListener?) {
    private var offer: Offer? = null
    private fun getOfferFromFirebase(offerId: String?) {
        val fStore = FirebaseFirestore.getInstance()
        fStore.collection("Offers")
            .document(offerId)
            .get()
            .addOnSuccessListener { ds ->
                offer = ds.toObject(Offer::class.java)
                offer.setOfferId(ds.id)
                listener.onGetOfferSuccess(offer)
            }
    }

    fun getOffer(offerId: String?) {
        getOfferFromFirebase(offerId)
    }
}