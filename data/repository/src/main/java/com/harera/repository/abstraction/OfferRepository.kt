package com.harera.repository.abstraction

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.harera.model.modelset.Offer

interface OfferRepository {

    fun getOfferById(offerId: String): Task<DocumentSnapshot>
    fun getOfferTypes(): Task<DataSnapshot>
    fun addOfferType(offerType: String): Task<Void>
    fun getOffers(offerType: String): Task<QuerySnapshot>
    fun addOffer(offer: Offer): Task<Void>
    fun getOffers(): Task<QuerySnapshot>
}