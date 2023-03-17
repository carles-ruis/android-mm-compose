package com.carles.settings.ui

import com.carles.settings.R
import com.carles.settings.Setting
import com.carles.settings.UserSettings
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SettingsMapperTest {

    private lateinit var mapper: SettingsMapper

    @Before
    fun setup() {
        mapper = SettingsMapper()
    }

    @Test
    fun `given toUi, when cache expiration setting is 0, then it maps to dont use cache text`() {
        mapper.toUi(UserSettings(0)).let { result ->
            assertEquals(1, result.count())
            val cacheCategory = result[0]
            assertEquals(R.string.preferences_cache, cacheCategory.title)
            assertEquals(1, cacheCategory.settings.count())
            val expirationSetting = cacheCategory.settings[0] as Setting.ListSetting
            assertEquals(R.string.preferences_cache_key, expirationSetting.key)
            assertEquals(R.string.preferences_cache_expiration, expirationSetting.title)
            assertEquals(R.string.preferences_cache_dont_use, expirationSetting.value)
        }
    }

    @Test
    fun `given toUi, when cache expiration setting is 1, then it maps to one minute text`() {
        mapper.toUi(UserSettings(1)).let { result ->
            val expiration = result[0].settings[0] as Setting.ListSetting
            assertEquals(R.string.preferences_cache_one_minute, expiration.value)
        }
    }

    @Test
    fun `given toUi, when cache expiration setting is 10, then it maps to ten minutes text`() {
        mapper.toUi(UserSettings(10)).let { result ->
            val expiration = result[0].settings[0] as Setting.ListSetting
            assertEquals(R.string.preferences_cache_ten_minutes, expiration.value)
        }
    }

    @Test
    fun `given toUi, when cache expiration setting is max integer, then it maps to never text`() {
        mapper.toUi(UserSettings(Int.MAX_VALUE)).let { result ->
            val expiration = result[0].settings[0] as Setting.ListSetting
            assertEquals(R.string.preferences_cache_never, expiration.value)
        }
    }

    @Test
    fun `given toUi, when cache expiration setting is invalid, then it maps to one minute text`() {
        mapper.toUi(UserSettings(-1)).let { result ->
            val expiration = result[0].settings[0] as Setting.ListSetting
            assertEquals(R.string.preferences_cache_one_minute, expiration.value)
        }
    }

    @Test
    fun `given toCacheExpirationTimeValue, when selected option is dont use, then returns 0`() {
        assertEquals(0, mapper.toCacheExpirationTimeValue(R.string.preferences_cache_dont_use))
    }

    @Test
    fun `given toCacheExpirationTimeValue, when selected option is one minute, then returns 1`() {
        assertEquals(1, mapper.toCacheExpirationTimeValue(R.string.preferences_cache_one_minute))
    }

    @Test
    fun `given toCacheExpirationTimeValue, when selected option is, then returns 10`() {
        assertEquals(10, mapper.toCacheExpirationTimeValue(R.string.preferences_cache_ten_minutes))
    }

    @Test
    fun `given toCacheExpirationTimeValue, when selected option is never, then returns max int`() {
        assertEquals(Int.MAX_VALUE, mapper.toCacheExpirationTimeValue(R.string.preferences_cache_never))
    }
}