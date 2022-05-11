package com.example.assignment.view

import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import assignment.R
import com.example.assignment.view.activities.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, true, true)

    @Before
    @Throws(Exception::class)
    fun setUp() {
        //launching the activity that is required to test
        activityRule.launchActivity(Intent())
        // initializing init for intents
        Intents.init()
    }

    @Test
    fun testSearch() {
        Espresso.onView(withId(R.id.editTextSearchMovie)).perform(typeText("rrr"))
        Espresso.onView(withId(R.id.btn)).perform(click())

        Espresso.onView(withId(R.id.rv)).apply {

        }
    }
}