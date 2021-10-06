package com.harera.hyperpanda

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class HyperPandaApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}