package com.harera.repository.firebase

import com.google.android.gms.tasks.Tasks
import com.google.firebase.Timestamp
import com.harera.model.modelset.CartItem
import com.harera.repository.repository.AuthManager
import com.harera.repository.repository.CartRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.random.Random

@HiltAndroidTest
class FirebaseCartRepositoryTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var carRepository: CartRepository

    @Inject
    lateinit var authManager: AuthManager

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun testAddCartItem() {
        val task = carRepository
            .addCartItem(
                CartItem(
                    uid = "mibMg1y2zSSQgZHCoj9oFde6oiU2",
                    productId = "lp85r7lRC4oYPStvBMb3",
                    time = Timestamp.now(),
                    quantity = Random.nextInt(5) + 1
                )
            )
        Tasks.await(task)
        Assert.assertTrue(task.isSuccessful)
    }

    @Test
    fun testRemoveICartItem() {
        val task = carRepository
            .removeCartItem(
                cartItemId = "HnYFLstRzxJUBf4T62bI"
            )
        Tasks.await(task)
        Assert.assertTrue(task.isSuccessful)
    }

    @Test
    fun testUpdateQuantity() {
        val task = carRepository
            .updateQuantity(
                cartItemId = "yVBmE1h0kfr0wcSip49b",
                quantity = 5
            )
        Tasks.await(task)
        Assert.assertTrue(task.isSuccessful)
    }

    @Test
    fun testUpdateUid() {
        val task = carRepository
            .updateItemUid(
                itemId = "yVBmE1h0kfr0wcSip49b",
                uid = "DH9WgqJyMSdGEJvMlygtZDspOjP2"
            )
        Tasks.await(task)
        Assert.assertTrue(task.isSuccessful)
    }

    @Test
    fun testGetCartItems() {
        val task = carRepository
            .getCartItems(
                uid = "kReJCbQzGZVJ5pefHqjIePfTIxC2"
            )
        val result = Tasks.await(task)
        Assert.assertTrue(task.isSuccessful)
        Assert.assertEquals(1, result.documents.size)
    }
}