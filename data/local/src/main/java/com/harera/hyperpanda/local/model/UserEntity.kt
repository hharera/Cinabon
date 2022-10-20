package com.harera.hyperpanda.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity
class UserEntity {
    lateinit var name: String
    lateinit var phoneNumber: String
    lateinit var address: Address
    @PrimaryKey
    lateinit var uid: String
}