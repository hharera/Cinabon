package com.harera.repository.impl

import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.harera.repository.abstraction.DBConstants.OFFERS
import com.harera.repository.abstraction.OfferRepository
import com.harera.repository.domain.Offer
import javax.inject.Inject

class OfferRepositoryImpl @Inject constructor(
    private val fStore: FirebaseFirestore,
    private val fDatabase: FirebaseDatabase,
) : OfferRepository {

    override fun getOffers(offerType: String) =
        fStore
            .collection(OFFERS)
            .whereEqualTo(Offer::offerTitle.name, offerType)
            .get()

    override fun addOffer(offer: Offer): Task<Void> =
        fStore
            .collection(OFFERS)
            .document()
            .apply {
                offer.offerId = id
            }
            .set(offer)

    override fun getOfferTypes() =
        fDatabase
            .reference
            .child(OFFERS)
            .get()

    override fun addOfferType(offerType: String) =
        fDatabase
            .reference
            .child(OFFERS)
            .child(offerType)
            .setValue(offerType)

    override fun getOfferById(offerId: String) =
        fStore.collection("Offers")
            .document(offerId)
            .get()

    override fun getOffers(): Task<QuerySnapshot> =
        fStore
            .collection(OFFERS)
            .get()
}

