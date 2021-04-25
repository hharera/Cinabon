package com.whiteside.cafe.model

class SignUpUser : User() {

    var code: String? = null
    var verificationCode: String? = null
    var password: String? = null
}