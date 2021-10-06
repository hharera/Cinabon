package com.harera.repository.firebase

import com.google.android.gms.tasks.Tasks
import com.google.firebase.Timestamp
import com.harera.data.remote.repository.AuthManager
import com.harera.data.remote.repository.WishListRepository
import com.harera.data.modelset.WishListItem
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class FirebaseWishlistRepositoryTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var wishListRepository: WishListRepository

    @Inject
    lateinit var authManager : AuthManager

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun testAddCartItem() {
        val task = wishListRepository
            .addWishListItem(
                WishListItem(
                    uid = "mibMg1y2zSSQgZHCoj9oFde6oiU2",
                    productId = "lp85r7lRC4oYPStvBMb3",
                    time = Timestamp.now(),
                )
            )
        Tasks.await(task)
        Assert.assertTrue(task.isSuccessful)
    }

    @Test
    fun testRemoveWishListItem() {
        val task = wishListRepository
            .removeWishListItem(
                productId = "ytlNIIpy1nJHIUDoPY9n",
                uid = authManager.getCurrentUser()!!.uid
            )
        Tasks.await(task)
        Assert.assertTrue(task.isSuccessful)
    }

    @Test
    fun testUpdateUid() {
        val task = wishListRepository
            .updateItemUid(
                itemId = "GtctIjSzijUso5hAkEwr",
                uid = "DH9WgqJyMSdGEJvMlygtZDspOjP2"
            )
        Tasks.await(task)
        Assert.assertTrue(task.isSuccessful)
    }

    @Test
    fun testGetCartItems() {
        val task = wishListRepository
            .getWishListItems(
                uid = "mibMg1y2zSSQgZHCoj9oFde6oiU2"
            )
        val result = Tasks.await(task)
        Assert.assertTrue(task.isSuccessful)
        Assert.assertEquals(1, result.documents.size)
    }
}