package com.carles.mm

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

// https://github.com/dannyroa/espresso-samples/blob/master/RecyclerView/app/src/androidTest/java/com/dannyroa/espresso_samples/recyclerview/RecyclerViewMatcher.java
fun withViewAt(position: Int, targetViewId: Int, recyclerViewId: Int): Matcher<View> = object : TypeSafeMatcher<View>() {
    var childView: View? = null

    override fun describeTo(description: Description) {
        description.appendText("atPositionOnView: $position")
    }

    public override fun matchesSafely(view: View): Boolean {
        if (childView == null) {
            val recyclerView = view.rootView.findViewById<RecyclerView>(recyclerViewId)

            childView = if (recyclerView.id == recyclerViewId) {
                recyclerView.findViewHolderForAdapterPosition(position)?.itemView
            } else {
                return false
            }
        }

        val targetView = childView?.findViewById<View>(targetViewId)
        return view === targetView
    }
}

// https://medium.com/mindorks/some-useful-custom-espresso-matchers-in-android-33f6b9ca2240
fun viewAtPosition(position: Int, targetViewId: Int, itemMatcher: Matcher<View>): Matcher<View> =
    object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has view id " + itemMatcher + " at position " + position);
        }

        override fun matchesSafely(recyclerView: RecyclerView): Boolean {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)!!
            val targetView = viewHolder.itemView.findViewById<View>(targetViewId)
            return itemMatcher.matches(targetView)
        }

    }

// https://stackoverflow.com/questions/31394569/how-to-assert-inside-a-recyclerview-in-espresso
fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> =
    object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

        override fun describeTo(description: Description) {
            description.appendText("has item at position " + position + ": " + itemMatcher)
        }

        override fun matchesSafely(recyclerView: RecyclerView): Boolean {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
            return itemMatcher.matches(viewHolder!!.itemView)
        }

    }

// https://medium.com/mindorks/some-useful-custom-espresso-matchers-in-android-33f6b9ca2240
fun recyclerViewSize(expectedCount: Int): Matcher<View> = object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
    override fun describeTo(description: Description) {
        description.appendText("RecyclerView should have $expectedCount items")
    }

    override fun matchesSafely(recyclerView: RecyclerView): Boolean {
        return recyclerView.adapter!!.itemCount == expectedCount
    }
}
