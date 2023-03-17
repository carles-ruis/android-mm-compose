package com.carles.common.data

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.carles.common.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(@ApplicationContext private val context: Context) {

    private val preferences by lazy { PreferenceManager.getDefaultSharedPreferences(context) }

    private val prefCacheKey = context.getString(R.string.preferences_cache_key)

    var cacheExpirationTime: Int
        get() = preferences.getInt(prefCacheKey, PREF_CACHE_DEFAULT_VALUE)
        set(value) {
            preferences.edit { putInt(prefCacheKey, value) }
        }

    companion object {
        private const val PREF_CACHE_DEFAULT_VALUE = 1
    }
}