package com.harera.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CategoryEntity {
    @PrimaryKey
    lateinit var categoryName: String
    lateinit var categoryImagerUrl: String
}