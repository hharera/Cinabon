package com.whiteside.cafe.api.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.whiteside.cafe.model.Offer

interface OfferRepository {

    fun getLastOffers(): Task<QuerySnapshot>
    fun getBestOffers(): Task<QuerySnapshot>
    fun getOfferById(offerId: String): Task<DocumentSnapshot>
    fun setLastOffer(offer: Offer): Task<Void>
    fun setBestOffer(offer: Offer): Task<Void>
    fun getBestOffer(offerId: String): Task<DocumentSnapshot>
    fun getLastOffer(offerId: String): Task<DocumentSnapshot>
}