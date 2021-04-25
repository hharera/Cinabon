package com.whiteside.cafe.api.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.whiteside.cafe.api.repository.OfferRepository
import com.whiteside.cafe.model.Offer
import javax.inject.Inject

class FirebaseOfferRepository @Inject constructor() : OfferRepository {
    val fStore by lazy { FirebaseFirestore.getInstance() }

    override fun getLastOffers() =
        fStore.collection("LastOffers")
            .get()

    override fun getBestOffers() =
        fStore.collection("BestOffers")
            .get()

    override fun getBestOffer(offerId: String) =
        fStore.collection("BestOffers")
            .document(offerId)
            .get()

    override fun getLastOffer(offerId: String) =
        fStore.collection("LastOffers")
            .document(offerId)
            .get()

    override fun getOfferById(offerId: String) =
        fStore.collection("Offers")
            .document(offerId)
            .get()

    override fun setLastOffer(offer: Offer): Task<Void> {
        offer.offerId =
            fStore.collection("LastOffers")
                .document()
                .id

        return fStore.collection("LastOffers")
            .document(offer.offerId)
            .set(offer)
    }

    override fun setBestOffer(offer: Offer): Task<Void> {
        offer.offerId =
            fStore.collection("BestOffers")
                .document()
                .id

        return fStore.collection("BestOffers")
            .document(offer.offerId)
            .set(offer)
    }
}

