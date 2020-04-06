package com.carles.core.data

import android.content.Context
import androidx.preference.PreferenceManager
import com.carles.core.R

class AppPreferences(private val context: Context) {

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    val cacheExpirationTime: Long
        get() = preferences.getString(
            context.getString(R.string.preferences_cache_key),
            context.getString(R.string.preferences_cache_default_value)
        )!!.toLong()
}