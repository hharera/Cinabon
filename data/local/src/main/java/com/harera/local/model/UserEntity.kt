package com.harera.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class UserEntity {
    lateinit var name: String
    lateinit var phoneNumber: String
    lateinit var address: Address
    @PrimaryKey
    lateinit var uid: String
}