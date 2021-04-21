package com.whiteside.cafe.common

import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity


open class BaseActivity : AppCompatActivity() {
    val connection: ConnectivityManager by lazy {
        application.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}