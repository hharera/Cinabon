package com.whiteside.cafe.model

open class User {
    private var UID: String? = null
    private var name: String? = null
    private var phoneNumber: String? = null
    fun getUID(): String? {
        return UID
    }

    fun setUID(UID: String?) {
        this.UID = UID
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getPhoneNumber(): String? {
        return phoneNumber
    }

    fun setPhoneNumber(phoneNumber: String?) {
        this.phoneNumber = phoneNumber
    }
}