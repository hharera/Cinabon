package com.whiteside.cafe.common

import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


open class BaseActivity : AppCompatActivity() {
    val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    val fStore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    val connection: ConnectivityManager by lazy {
        application.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}