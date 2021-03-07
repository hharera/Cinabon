package com.whiteside.cafe.Shop

import Model.Offer
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

class OffersPresenter(var listener: OnGetOffersListener?) {
    private fun getOffersFromFirebase() {
        val fStore = FirebaseFirestore.getInstance()
        fStore.collection("Offers")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (ds in task.result.documents) {
                        val offer = ds.toObject(Offer::class.java)
                        offer.setOfferId(ds.id)
                        if (offer.getEndTime().seconds - Timestamp.now().seconds > 0) listener.onGetOfferSuccess(
                            offer
                        )
                    }
                } else {
                    listener.onGetOfferFailed(task.exception)
                }
            }
    }

    fun getOffers() {
        getOffersFromFirebase()
    }
}