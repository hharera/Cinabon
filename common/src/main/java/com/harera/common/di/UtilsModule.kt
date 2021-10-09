package com.harera.common.di

import android.app.Application
import com.harera.common.network.ConnectionLiveData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class UtilsModule {

    companion object {
        @Provides
        @Singleton
        fun provideConnectionLiveData(application: Application) =
            ConnectionLiveData(application)
    }
}