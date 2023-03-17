package com.carles.mm

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.carles.mm.ui.MainActivity
import com.carles.mm.ui.onIconWithContentDescription
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainActivityTest {

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @Test
    fun mainActivity_navigation() {
        with(composeRule) {
            val monstersTitle = activity.getString(R.string.appname)
            val topBarTitleTag = activity.getString(R.string.tag_top_bar_title)
            val settingsDescription = activity.getString(R.string.settings)
            val settingsTitle = activity.getString(R.string.settings)
            val backDescription = activity.getString(R.string.description_back_arrow)

            onRoot(useUnmergedTree = true).printToLog("mainActivity_navigation")
            // monsters screen: check title
            onNodeWithTag(topBarTitleTag).assertTextEquals(monstersTitle)
            // click on settings icon
            onIconWithContentDescription(settingsDescription).performClick()
            // settings screen: check title
            onNodeWithTag(topBarTitleTag).assertTextEquals(settingsTitle)
            // navigate back
            onIconWithContentDescription(backDescription).performClick()
            // monsters screen: check title
            onNodeWithTag(topBarTitleTag).assertTextEquals(monstersTitle)
            // click on element in the monsters list
            onNodeWithText("molduga").performClick()
            // monster detail: check title is displayed
            onNodeWithTag(topBarTitleTag).assertTextEquals("molduga")
            // navigate back
            onIconWithContentDescription(backDescription).performClick()
            // monsters screen: check title
            onNodeWithTag(topBarTitleTag).assertTextEquals(monstersTitle)
            // leave app
            activityRule.scenario.onActivity { action ->
                action.onBackPressedDispatcher.onBackPressed()
            }
            val isDestroyed = activityRule.scenario.state == Lifecycle.State.RESUMED
            val isCreated = activityRule.scenario.state == Lifecycle.State.CREATED
            Assert.assertTrue(isDestroyed || isCreated)
        }
    }
}