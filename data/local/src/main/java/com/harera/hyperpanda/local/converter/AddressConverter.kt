package com.harera.local.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.harera.model.modelset.Address

@ProvidedTypeConverter
class AddressConverter {

    @TypeConverter
    fun addressToString(address: Address?): String? {
        return address?.let {
            Gson().toJson(it)
        }
    }

    @TypeConverter
    fun fromAddress(value: String?): Address? {
        return value?.let {
            Gson().fromJson(it, Address::class.java)
        }
    }
}