package com.harera.hyperpanda.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.harera.model.modelget.Category
import com.harera.model.modelget.Product

@Dao
interface MarketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(list: List<Category>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(list: Category)

    @Insert
    fun insertProducts(list: List<Product>)

    @Query("select * from category")
    fun getCategories(): List<Category>
}