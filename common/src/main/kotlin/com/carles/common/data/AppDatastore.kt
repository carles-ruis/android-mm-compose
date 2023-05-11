package com.carles.common.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.carles.common.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDatastore @Inject constructor(@ApplicationContext private val context: Context) {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(DATASTORE_NAME)

    private val cacheKey = intPreferencesKey(context.getString(R.string.preferences_cache_key))

    fun getCacheExpirationTime(): Flow<Int> {
        return context.datastore.data.map { prefs ->
            prefs[cacheKey] ?: PREF_CACHE_DEFAULT
        }
    }

    suspend fun setCacheExpirationTime(expirationTime: Int) {
        context.datastore.edit { prefs ->
            prefs[cacheKey] = expirationTime
        }
    }

    companion object {
        private const val DATASTORE_NAME = "app_datastore"

        private const val PREF_CACHE_DEFAULT = 1
    }
}