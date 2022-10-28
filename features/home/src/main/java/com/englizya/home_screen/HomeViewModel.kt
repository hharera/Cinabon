package com.englizya.home_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.UserDataStore
import com.englizya.model.model.Announcement
import com.englizya.model.model.Offer
import com.englizya.model.model.User
import com.englizya.repository.AnnouncementRepository
import com.englizya.repository.OfferRepository
import com.englizya.repository.UserRepository
import com.englizya.repository.utils.Resource

class HomeViewModel constructor(
    private val userRepository: UserRepository,
    private val offerRepository: OfferRepository,
    private val announcementRepository: AnnouncementRepository,
    private val dataStore: UserDataStore,
) : BaseViewModel() {

    fun getUser(forceOnline: Boolean = false): LiveData<Resource<User>> {
       return userRepository
            .getUser(dataStore.getToken(), forceOnline)
            .asLiveData()
    }

    fun getOffers(forceOnline: Boolean = false): LiveData<Resource<List<Offer>>> {
        return offerRepository
            .getAllOffers(forceOnline)
            .asLiveData()
    }

    fun getAnnouncements(forceOnline: Boolean = false): LiveData<Resource<List<Announcement>>> {
        return announcementRepository
            .getAllAnnouncement(forceOnline)
            .asLiveData()
    }
}
