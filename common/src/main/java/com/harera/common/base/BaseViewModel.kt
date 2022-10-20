package com.harera.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harera.common.extension.toException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    private val _connectivity: MutableLiveData<Boolean> = MutableLiveData(true)
    val connectivity: LiveData<Boolean> = _connectivity

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _exception: MutableLiveData<Exception?> = MutableLiveData()
    val exception: LiveData<Exception?> = _exception

    fun updateException(exception: Exception?) {
        exception?.printStackTrace()
    }

    fun updateLoading(state: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            _loading.value = state
        }
    }

    fun handleException(throwable: Throwable?) {
        val toException = throwable?.toException()
        _exception.postValue(toException)
    }

    fun updateException(exception: Throwable?) {
        exception?.printStackTrace()
    }

    fun updateConnectivity(connectivity: Boolean) {
        _connectivity.postValue(connectivity)
    }
}