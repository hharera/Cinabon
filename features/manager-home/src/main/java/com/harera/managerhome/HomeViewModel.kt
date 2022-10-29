package com.harera.managerhome

import com.harera.common.base.BaseViewModel
import com.harera.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel() {

}
