package com.carles.mm

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.carles.mm.ui.MainActivity
import com.carles.mm.ui.onDialogNodeWithText
import com.carles.mm.ui.onIconWithContentDescription
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class SettingsScreenTest {

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @Test
    fun settingsScreen_testCacheExpirationSelection() {
        with(composeRule) {
            val settingsDescription = activity.getString(R.string.settings)
            val cacheExpirationText = activity.getString(R.string.preferences_cache_expiration)
            val tagSettingsDialog = activity.getString(R.string.tag_settings_dialog)
            val cacheDontUseText = activity.getString(R.string.preferences_cache_dont_use)
            val cacheOneMinuteText = activity.getString(R.string.preferences_cache_one_minute)
            val cacheTenMinutesText = activity.getString(R.string.preferences_cache_ten_minutes)
            val cacheNeverText = activity.getString(R.string.preferences_cache_never)
            val cancel = activity.getString(android.R.string.cancel)

            onRoot(useUnmergedTree = true).printToLog("settingsScreen_testCacheExpirationSelection")
            onIconWithContentDescription(settingsDescription).performClick()

            // check cache expiration dialog
            onNodeWithText(cacheExpirationText).assertIsDisplayed().performClick()
            onNodeWithTag(tagSettingsDialog).assertIsDisplayed()
            onDialogNodeWithText(cacheDontUseText).assertIsDisplayed()
            onDialogNodeWithText(cacheOneMinuteText).assertIsDisplayed()
            onDialogNodeWithText(cacheTenMinutesText).assertIsDisplayed()
            onDialogNodeWithText(cacheNeverText).assertIsDisplayed()
            onDialogNodeWithText(cancel).assertIsDisplayed()

            // check one minute cache selected
            onDialogNodeWithText(cacheOneMinuteText).performClick()
            onNodeWithTag(tagSettingsDialog).assertDoesNotExist()
            onNodeWithText(cacheDontUseText).assertDoesNotExist()
            onNodeWithText(cacheOneMinuteText).assertIsDisplayed()
            onNodeWithText(cacheTenMinutesText).assertDoesNotExist()
            onNodeWithText(cacheNeverText).assertDoesNotExist()

            // check ten minutes cache selected
            onNodeWithText(cacheExpirationText).performClick()
            onDialogNodeWithText(cacheTenMinutesText).performClick()
            onNodeWithText(cacheOneMinuteText).assertDoesNotExist()
            onNodeWithText(cacheTenMinutesText).assertIsDisplayed()

            // check cancel button on cache selection
            onNodeWithText(cacheExpirationText).performClick()
            onDialogNodeWithText(cancel).performClick()
            onNodeWithText(cacheOneMinuteText).assertDoesNotExist()
            onNodeWithText(cacheTenMinutesText).assertIsDisplayed()
        }
    }
}