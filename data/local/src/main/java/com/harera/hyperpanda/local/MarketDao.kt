package com.harera.hyperpanda.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.harera.local.model.Category
import com.harera.local.model.Product

@Dao
interface MarketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(list: List<Category>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(list: Category)

    @Insert
    fun insertProducts(list: List<Product>)
}