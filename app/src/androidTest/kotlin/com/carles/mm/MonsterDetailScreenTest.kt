package com.carles.mm

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.carles.mm.ui.MainActivity
import com.carles.mm.ui.onIconWithContentDescription
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalTestApi::class)
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MonsterDetailScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun monsterDetailScreen_checkContent() {
        with(composeRule) {
            val monsterListTag = activity.getString(R.string.tag_monsters_list)
            val monsterLocations = activity.getString(R.string.tag_monster_locations)
            val monsterDescription = activity.getString(R.string.tag_monster_description)
            val monsterImage = activity.getString(R.string.tag_monster_image)
            val backDescription = activity.getString(R.string.description_back_arrow)

            onRoot(useUnmergedTree = true).printToLog("monsterDetailScreen_checkContent")

            // check monster list rows
            waitUntilAtLeastOneExists(hasTestTag(monsterListTag))
            onNodeWithTag(monsterListTag).apply {
                onChildAt(2).assertTextEquals("fire keese")
                onChildAt(3).assertTextEquals("frost talus")
                onNodeWithText("thunderblight ganon").assertDoesNotExist()
                performScrollToIndex(15)
                onNodeWithText("thunderblight ganon").assertIsDisplayed()
            }

            // check monster detail
            onNodeWithText("molduga").performClick()
            onNodeWithTag(monsterLocations).assertTextEquals("Gerudo Desert")
            onNodeWithTag(monsterDescription).assertTextContains("This massive monster swims", true)
            onNodeWithTag(monsterImage).assertIsDisplayed()
            onIconWithContentDescription(backDescription).performClick()

            // check monster detail without image
            onNodeWithText("guardian scout iv").performClick()
            onNodeWithTag(monsterLocations).assertTextEquals("")
            onNodeWithTag(monsterDescription).assertTextContains("An ancient civilization", true)
            onNodeWithTag(monsterImage).assertIsDisplayed()
        }
    }
}