package com.carles.mm

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.carles.mm.extensions.atPosition
import com.carles.mm.extensions.recyclerViewSize
import com.carles.mm.ui.view.PoiListActivity
import junit.framework.Assert.assertTrue
import org.hamcrest.Matchers.startsWith
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class PoiTest {

    @get:Rule
    val activityRule = ActivityTestRule(PoiListActivity::class.java)

    @Test
    fun showPoiListAndNavigateToPoiDetail() {
        // display POI LIST, check first and last rows, click on last item
        val appName = activityRule.activity.resources.getString(R.string.app_name)
        onView(withId(R.id.toolbar)).check(matches(hasDescendant(withText(appName))))

        onView(withId(R.id.poilist_recyclerview)).check(matches(recyclerViewSize(52)))
        onView(withId(R.id.poilist_recyclerview))
            .check(matches(atPosition(2, withText("Hospital de Sant Pau"))))
        onView(withId(R.id.poilist_recyclerview))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(50))
            .check(matches(atPosition(50, withText("Torre de Collserola"))))
        onView(withId(R.id.poilist_recyclerview))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(51, click()))

        // open POI DETAIL, scroll to check phone number
        onView(withId(R.id.toolbar)).check(matches(hasDescendant(withText("Il·luminació Torre Agbar"))))
        onView(withId(R.id.poidetail_address_textview)).check(matches(withText(startsWith("Avinguda Diagonal"))))
        onView(withId(R.id.poidetail_description_textview)).check(matches(withText(startsWith("One of the most characteristic"))))
        onView(withId(R.id.poidetail_phone_textview))
            .perform(scrollTo())
            .check(matches(withText("Tel: +3493 342 20 00")))
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())

        // again on POI LIST, click on first item
        onView(withId(R.id.toolbar)).check(matches(hasDescendant(withText(appName))))
        onView(withId(R.id.poilist_recyclerview))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(0))
        Thread.sleep(1000)
        onView(withId(R.id.poilist_recyclerview))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        // first POI DETAIL, check and go back twice to close app
        onView(withId(R.id.toolbar)).check(matches(hasDescendant(withText("Casa Batlló"))))
        onView(withId(R.id.poidetail_address_textview)).check(matches(withText("Paseo de Gracia, 43, 08007 Barcelona")))
        onView(withId(R.id.poidetail_description_textview)).check(matches(withText(startsWith("Casa Batlló is a key feature"))))
        onView(withId(R.id.poidetail_phone_textview)).check(matches(withText("info@casabatllo.cat")))
        Espresso.pressBack()
        Espresso.pressBackUnconditionally()
        assertTrue(activityRule.activity.isDestroyed)
    }

}
