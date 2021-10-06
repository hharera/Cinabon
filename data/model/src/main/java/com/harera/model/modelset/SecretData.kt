package com.harera.model.modelset

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

class SecretData {

    private fun initFirebase(context: Context) {
        if (FirebaseApp.getApps(context).isEmpty()) {
            val options = FirebaseOptions.Builder()
                //old ApplicationId .setApplicationId("1:261802668850:android:3d7d1afc9f8f3d21b0dd2b")
                .setApplicationId("1:261802668850:android:ef229656eca7e9c0b0dd2b")
                .setApiKey("AIzaSyDNwH033gu0gBUBRcINWhNHfbeameUpFFw")
                .setProjectId("ecommerce-55b58")
                .build()
            FirebaseApp.initializeApp(context, options, "Cafe")
        }
    }
}