package com.harera.hyperpanda

import android.app.Application
import com.google.firebase.FirebaseApp
import com.harera.hyperpanda.local.MarketDao
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class HyperPandaApp : Application() {

    @Inject
    lateinit var database: MarketDao

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(applicationContext)
    }
}