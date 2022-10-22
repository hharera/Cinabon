package com.harera.repository

import com.google.android.gms.tasks.Tasks
import com.harera.service.UserService
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ServiceDomainUserServiceTest {

    private val TAG = "AuthManagerTest"

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var userService: UserService

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun checkUser() {
        val firebaseUser = userService.getCurrentUser()
        Assert.assertNotNull(firebaseUser)
    }

    @Test
    fun loginAnonymously() {
        val task = userService.loginAnonymously()
        Tasks.await(task)
        Assert.assertNull(task.exception)
    }

    @Test
    fun signInWithCredential() {
//        val task = authManager.signInWithCredential(PhoneAuthCredential.zzb("+201062227714", ""))
//        Tasks.await(task)
//        Assert.assertNull(task.exception)
    }

    @Test
    fun signInWithAnonEmail() {
        val task = userService.signInWithEmailAndPassword("hassan.shaban.harera@gmail.com", "harera")
        Tasks.await(task)
        Assert.assertNull(task.exception)
    }

    @Test
    fun signOut() {
        val task = userService.signOut()
    }
}