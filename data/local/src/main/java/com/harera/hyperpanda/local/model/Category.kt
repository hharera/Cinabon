package com.harera.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity
class Category {
    @PrimaryKey
    lateinit var categoryName: String
    lateinit var categoryImagerUrl: String
}