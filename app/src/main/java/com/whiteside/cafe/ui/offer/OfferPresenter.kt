package com.whiteside.cafe.ui.offer

import com.google.firebase.firestore.FirebaseFirestore
import com.whiteside.cafe.model.Offer
import com.whiteside.cafe.ui.shop.OnGetOffersListener

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