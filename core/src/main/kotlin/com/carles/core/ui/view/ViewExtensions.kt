package com.carles.core.ui.view

import android.content.res.Resources
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun ViewGroup.inflate(@LayoutRes layoutRes: Int) = LayoutInflater.from(context).inflate(layoutRes, this, false)

inline fun Fragment.consumeMenuClick(f: () -> Unit): Boolean {
    f()
    return true
}

fun AppCompatActivity.getStrings(ids: List<Int>) = ids.map { getString(it) }.toTypedArray()

fun Int.toPx() = this * Resources.getSystem().displayMetrics.density

fun Int.toDp() = this / Resources.getSystem().displayMetrics.density

fun View.setDebounceClickListener(action: () -> Unit, debounceTime: Long = 2000L) {
    var lastClickTime: Long = 0
    this.setOnClickListener(object : View.OnClickListener {
        override fun onClick(v: View?) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            lastClickTime = SystemClock.elapsedRealtime()
            action()
        }
    })
}
