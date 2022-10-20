package com.harera.hyperpanda.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.harera.hyperpanda.local.converter.AddressConverter
import com.harera.hyperpanda.local.model.OfferEntity
import com.harera.hyperpanda.local.model.ProductEntity
import com.harera.local.converter.TimestampConverter
import com.harera.hyperpanda.local.model.CartItemEntity
import com.harera.hyperpanda.local.model.CategoryEntity
import com.harera.hyperpanda.local.model.UserEntity
import com.harera.hyperpanda.local.model.WishItemEntity


@Database(
    version = 2,
    entities = [
        CartItemEntity::class,
        CategoryEntity::class,
        OfferEntity::class,
        UserEntity::class,
        WishItemEntity::class,
        ProductEntity::class,
    ],
    exportSchema = true,
)
@TypeConverters(TimestampConverter::class, AddressConverter::class)
abstract class MarketDatabase : RoomDatabase() {
    abstract fun getMarketDao() : MarketDao
}