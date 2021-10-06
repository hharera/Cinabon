package com.harera.model.modelget

import com.google.firebase.firestore.IgnoreExtraProperties
import com.harera.model.modelset.Address

@IgnoreExtraProperties
class User {
    lateinit var name: String
    lateinit var phoneNumber: String
    lateinit var address: Address
    lateinit var uid: String
}