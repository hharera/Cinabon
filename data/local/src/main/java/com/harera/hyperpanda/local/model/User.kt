package com.harera.model.modelget

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties
import com.harera.model.modelset.Address

@IgnoreExtraProperties
@Entity
class User {
    lateinit var name: String
    lateinit var phoneNumber: String
    lateinit var address: Address
    @PrimaryKey
    lateinit var uid: String
}