package com.carles.mm.features

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.carles.mm.MainActivity
import com.carles.mm.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SettingsTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun showAndSetSettings() {
        // display settings screen
        onView(withId(R.id.poilist_settings_menu)).perform(click())
        onView(withId(R.id.settings_toolbar)).check(matches(hasDescendant(withText(R.string.settings))))

        // open cache dialog and select several options
        onView(withText(R.string.preferences_cache_expiration)).check(matches(isDisplayed())).perform(click())
        for (entry in activityTestRule.activity.resources.getStringArray(R.array.preferences_cache_entries)) {
            onView(withText(entry)).check(matches(isDisplayed()))
        }
        onView(withText(R.string.preferences_cache_dont_use)).perform(click())
        onView(withText(R.string.preferences_cache_dont_use)).check(matches(isDisplayed()))
        onView(withText(R.string.preferences_cache_expiration)).perform(click())
        onView(withText(android.R.string.cancel)).perform(click())
        onView(withText(R.string.preferences_cache_expiration)).perform(click())
        onView(withText(R.string.preferences_cache_ten_minutes)).perform(click())
        onView(withText(R.string.preferences_cache_ten_minutes)).check(matches(isDisplayed()))
    }

}