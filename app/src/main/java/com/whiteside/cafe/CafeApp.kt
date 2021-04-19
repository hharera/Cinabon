package com.whiteside.cafe

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class CafeApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initFirebase()
    }

    private fun initFirebase() {
        if (FirebaseApp.getApps(applicationContext).isNullOrEmpty()) {
            val options = FirebaseOptions.Builder()
                .setApplicationId("1:261802668850:android:c88ecf82d1a11c7bb0dd2b")
                .setApiKey("AIzaSyD067FAA98I17gvHxINUNmjo4GeObS2DSQ")
                .setProjectId("ecommerce-55b58")
                .build()
            FirebaseApp.initializeApp(applicationContext, options, "Cafe")
        }
    }
}