package com.harera.home_screen

import com.harera.common.base.BaseViewModel
import com.harera.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel() {

//    fun getUser(forceOnline: Boolean = false): LiveData<Resource<User>> {
//       return userRepository
//            .getUser(dataStore.getToken(), forceOnline)
//            .asLiveData()
//    }
}
