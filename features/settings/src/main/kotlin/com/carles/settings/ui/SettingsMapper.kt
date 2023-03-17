package com.carles.settings.ui

import androidx.annotation.StringRes
import com.carles.settings.R
import com.carles.settings.Setting
import com.carles.settings.SettingsCategory
import com.carles.settings.SettingsUi
import com.carles.settings.UserSettings
import javax.inject.Inject
import kotlin.collections.mapOf

class SettingsMapper @Inject constructor() {

    companion object {
        private const val CACHE_DONT_USE_VALUE = 0
        private const val CACHE_ONE_MINUTE = 1
        private const val CACHE_TEN_MINUTES = 10
        private const val CACHE_NEVER = Int.MAX_VALUE
    }

    private val cacheExpirationTimes: Map<Int, Int> = mapOf(
        CACHE_DONT_USE_VALUE to R.string.preferences_cache_dont_use,
        CACHE_ONE_MINUTE to R.string.preferences_cache_one_minute,
        CACHE_TEN_MINUTES to R.string.preferences_cache_ten_minutes,
        CACHE_NEVER to R.string.preferences_cache_never
    )

    fun toUi(userSettings: UserSettings): SettingsUi {
        return listOf(
            SettingsCategory(
                title = R.string.preferences_cache,
                settings = listOf(
                    Setting.ListSetting(
                        key = R.string.preferences_cache_key,
                        title = R.string.preferences_cache_expiration,
                        value = cacheExpirationTimes[userSettings.cacheExpirationTime] ?: R.string.preferences_cache_one_minute,
                        options = cacheExpirationTimes.values.toList()
                    )
                )
            )
        )
    }

    fun toCacheExpirationTimeValue(@StringRes selectedOption: Int): Int =
        cacheExpirationTimes.filter { selectedOption == it.value }.keys.first()
}