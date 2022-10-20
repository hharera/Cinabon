package com.englizya.datastore.di

import android.app.Application
import android.content.Context
import com.englizya.datastore.UserDataStore
import org.koin.dsl.module


val dataStoreModule = module {

    single<Context> {
        (get() as Application).applicationContext
    }

    single {
        UserDataStore(get())
    }
}