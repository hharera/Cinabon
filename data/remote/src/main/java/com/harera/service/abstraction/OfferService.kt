package com.harera.service.abstraction

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.harera.service.domain.ServiceDomainOffer

interface OfferService {

    fun getOfferById(offerId: String): Task<DocumentSnapshot>
    fun getOfferTypes(): Task<DataSnapshot>
    fun addOfferType(offerType: String): Task<Void>
    fun getOffers(offerType: String): Task<QuerySnapshot>
    fun addOffer(serviceDomainOffer: ServiceDomainOffer): Task<Void>
    fun getOffers(): Task<QuerySnapshot>
}