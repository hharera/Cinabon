package com.harera.repository.firebase

import com.google.android.gms.tasks.Tasks
import com.google.type.LatLng
import com.harera.model.modelset.User
import com.harera.repository.repository.UserRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import com.harera.local.model.User as UserGet

@HiltAndroidTest
class FirebaseUserRepositoryTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun testAddNewUser() {
        val task = userRepository.addUser(
            User(
                phoneNumber = "+201062227714",
                address = LatLng.newBuilder().build(),
                uid = "mibMg1y2zSSQgZHCoj9oFde6oiU2",
                firstName = "Hassan",
                lastName = "Hassan",
            )
        )
        Tasks.await(task)
    }

    @Test
    fun testRemoveUser() {
        val task = userRepository.removeUser("mibMg1y2zSSQgZHCoj9oFde6oiU2")
        Tasks.await(task)
    }

    @Test
    fun testGetUser() {
        val task = userRepository.getUser(
            uid = "mibMg1y2zSSQgZHCoj9oFde6oiU2",
        )
        Tasks.await(task).toObject(UserGet::class.java)!!
    }
}