package com.harera.local.di

import android.app.Application
import androidx.room.Room
import com.harera.local.MarketDao
import com.harera.local.MarketDatabase
import com.harera.local.OfferDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun bindMarketDatabase(application: Application): MarketDatabase =
        Room
            .databaseBuilder(
                application,
                MarketDatabase::class.java,
                "Market"
            )
//            .addTypeConverter(TimestampConverter::class)
//            .addTypeConverter(AddressConverter::class)
            .build()

    @Provides
    @Singleton
    fun bindMarketDao(database: MarketDatabase): MarketDao =
        database.getMarketDao()

    @Provides
    @Singleton
    fun provideOfferDao(database: MarketDatabase): OfferDao =
        database.getOfferDao()
}