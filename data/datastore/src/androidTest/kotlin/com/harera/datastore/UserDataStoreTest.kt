package com.harera.datastore

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.harera.datastore.di.dataStoreModule
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject


class UserDataStoreTest : KoinTest {

    private val userDataStore: UserDataStore by inject()

//    @get:Rule
//    val koinTestRule = KoinTestRule.create {
//        printLogger()
//        modules()
//    }

    @Before
    fun setup() {
        startKoin {
            androidContext(
                ApplicationProvider.getApplicationContext<Application>()
            )
            loadKoinModules(
                dataStoreModule
            )
        }
    }

    @Test
    fun testSetToken() {
        assertThat(
            userDataStore.getLanguage()
        ).isNull()
    }

    @Test
    fun testNameToken() {
        assertThat(
            userDataStore.setUserName("Hassan")
        ).isNotNull()
    }

    fun testSetLanguage() {}

    fun testGetLanguage() {}
}