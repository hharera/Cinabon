package com.harera.common.internet

import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.viewModels
import com.harera.common.base.BaseActivity
import com.harera.common.network.ConnectionLiveData
import com.harera.common.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoInternetActivity : BaseActivity() {
    @Inject
    lateinit var connectionLiveData: ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_internet)

        connectionLiveData.observe(this) {
            if (it) {
                finish()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}