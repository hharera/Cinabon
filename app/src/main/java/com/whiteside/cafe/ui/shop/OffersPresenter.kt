package com.whiteside.cafe.ui.shop

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.whiteside.cafe.model.Offer

class OffersPresenter(var listener: OnGetOffersListener) {
    private fun getOffersFromFirebase() {
        val fStore = FirebaseFirestore.getInstance()
        fStore.collection("Offers")
            .get()
            .addOnSuccessListener {
                for (ds in it.documents) {
                    val offer = ds.toObject(Offer::class.java)!!
                    offer.offerId = (ds.id)
                    if (offer.endTime.seconds - Timestamp.now().seconds > 0) listener.onGetOfferSuccess(
                        offer
                    )
                }
            }
            .addOnFailureListener {
                listener.onGetOfferFailed(it)
            }
    }

    fun getOffers() {
        getOffersFromFirebase()
    }
}