package com.harera.service.impl

import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.harera.service.abstraction.DBConstants.OFFERS
import com.harera.service.abstraction.OfferService
import com.harera.service.domain.ServiceDomainOffer
import javax.inject.Inject

class OfferServiceImpl @Inject constructor(
    private val fStore: FirebaseFirestore,
    private val fDatabase: FirebaseDatabase,
) : OfferService {

    override fun getOffers(offerType: String) =
        fStore
            .collection(OFFERS)
            .whereEqualTo(ServiceDomainOffer::offerTitle.name, offerType)
            .get()

    override fun addOffer(serviceDomainOffer: ServiceDomainOffer): Task<Void> =
        fStore
            .collection(OFFERS)
            .document()
            .apply {
                serviceDomainOffer.offerId = id
            }
            .set(serviceDomainOffer)

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

