package com.harera.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(): ViewModel() {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _exception: MutableLiveData<Exception?> = MutableLiveData()
    val exception: LiveData<Exception?> = _exception

    fun updateException(exception: Exception?) {
        viewModelScope.launch(Dispatchers.Main) {
            _exception.value = exception
        }
    }

    fun updateLoading(state: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            _loading.value = state
        }
    }
}