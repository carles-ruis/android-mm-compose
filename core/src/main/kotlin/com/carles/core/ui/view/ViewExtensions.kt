package com.carles.core.ui.view

import android.content.res.Resources
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.carles.core.R
import com.google.android.material.appbar.MaterialToolbar

fun ViewGroup.inflate(@LayoutRes layoutRes: Int) = LayoutInflater.from(context).inflate(layoutRes, this, false)

inline fun AppCompatActivity.consume(f: () -> Unit): Boolean {
    f()
    return true
}

fun AppCompatActivity.getStrings(ids: List<Int>) = ids.map { getString(it) }.toTypedArray()

fun AppCompatActivity.initDefaultToolbar(): MaterialToolbar {
    val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
    setSupportActionBar(toolbar)
    supportActionBar?.apply {
        setDisplayHomeAsUpEnabled(true)
        setDisplayShowHomeEnabled(true)
    }
    toolbar.setNavigationOnClickListener { onBackPressed() }
    return toolbar
}

fun Int.toPx() = this / Resources.getSystem().displayMetrics.density

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