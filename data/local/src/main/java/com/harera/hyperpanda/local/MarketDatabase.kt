package com.harera.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.harera.hyperpanda.local.MarketDao
import com.harera.local.converter.AddressConverter
import com.harera.local.converter.TimestampConverter
import com.harera.local.model.*


@Database(
    version = 2,
    entities = [
        CartItem::class,
        Category::class,
        Offer::class,
        User::class,
        WishItem::class,
        Product::class,
    ],
    exportSchema = true,
)
@TypeConverters(TimestampConverter::class, AddressConverter::class)
abstract class MarketDatabase : RoomDatabase() {
    abstract fun getMarketDao() : MarketDao
}