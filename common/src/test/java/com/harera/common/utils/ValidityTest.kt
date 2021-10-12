package com.harera.common.utils

import junit.framework.TestCase
import org.junit.Test

class ValidityTest : TestCase() {

    @Test
    fun checkPhoneNumberValidity() {
        assertEquals(Validity.checkPhoneNumber("01062227714"), true )
    }
}