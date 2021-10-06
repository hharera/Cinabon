package com.harera.model.modelset

data class User(
    var phoneNumber: String,
    val address: com.google.type.LatLng,
    var uid: String,
    var imageUrl: String? = null,
    val firstName: Any,
    val lastName: Any,
)

