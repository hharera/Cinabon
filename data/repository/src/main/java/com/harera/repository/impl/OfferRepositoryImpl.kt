package com.harera.repository.impl

import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.harera.local.OfferDao
import com.harera.repository.DBConstants.OFFERS
import com.harera.repository.OfferRepository
import com.harera.repository.domain.Offer
import com.harera.repository.mapper.OfferMapper
import com.harera.repository.uitls.Resource
import com.harera.repository.uitls.networkBoundResource
import com.harera.service.OfferService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfferRepositoryImpl @Inject constructor(
    private val fStore: FirebaseFirestore,
    private val fDatabase: FirebaseDatabase,
    private val offerService: OfferService,
    private val offerDao: OfferDao,
) : OfferRepository {

    override fun getOffers(offerType: String) =
        fStore
            .collection(OFFERS)
            .whereEqualTo(Offer::offerTitle.name, offerType)
            .get()

    override fun insertOffer(offer: Offer): Task<Void> =
        fStore
            .collection(OFFERS)
            .document()
            .apply {
                offer.offerId = id
            }
            .set(offer)

    override fun getOfferCategories() =
        fDatabase
            .reference
            .child(OFFERS)
            .get()

    override fun insertOfferCategory(offerCategory: String) =
        fDatabase
            .reference
            .child(OFFERS)
            .child(offerCategory)
            .setValue(offerCategory)

    override fun getOffer(
        offerId: String,
        forceOnline: Boolean
    ): Flow<Resource<Offer>> =
        networkBoundResource(
            query = {
                offerService.getOffer(offerId)!!.let {
                    OfferMapper.toOffer(it)
                }
            },
            fetch = {
                offerDao.getOffer(offerId).let {
                    OfferMapper.toOffer(it)
                }
            },
            saveFetchResult = { offer ->
                OfferMapper.toOfferEntity(offer).let {
                    offerDao.insertOffer(it)
                }
            },
            shouldFetch = {
                forceOnline
            }
        )

    override fun getOffers(forceOnline: Boolean): Flow<Resource<List<Offer>>> =
        networkBoundResource(
            query = {
                offerService.getOffers().map {
                    OfferMapper.toOffer(it)
                }
            },
            fetch = {
                offerDao.getOffers().map {
                    OfferMapper.toOffer(it)
                }
            },
            saveFetchResult = { offers ->
                offers.map {
                    OfferMapper.toOfferEntity(it)
                }.forEach {
                    offerDao.insertOffer(it)
                }
            },
            shouldFetch = {
                forceOnline
            }
        )
}

