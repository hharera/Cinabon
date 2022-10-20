package com.harera.hyperpanda.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity
class CategoryEntity {
    @PrimaryKey
    lateinit var categoryName: String
    lateinit var categoryImagerUrl: String
}