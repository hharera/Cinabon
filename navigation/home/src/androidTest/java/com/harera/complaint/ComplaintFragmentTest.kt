package com.harera.complaint

import android.content.Intent
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.harera.profile.ProfileFragment
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ComplaintFragmentTest {

    @get:Rule
    val activityRule = ActivityTestRule(SplashA::class.java)

    @Before
    fun setUp() {
        ActivityScenario
        FragmentScenario.launch(ProfileFragment::class.java)
    }

    @Test
    fun validateIntentSentToPackage() {
        return Intent(InstrumentationRegistry.getInstrumentation().context, MainActivity::class.java)
            .apply {
                putExtra("TEST", "TEST")
            }
    }

    @Test
    fun listGoesOverTheFold() {
    }
}