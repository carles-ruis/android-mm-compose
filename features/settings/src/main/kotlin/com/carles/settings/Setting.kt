package com.carles.settings

import androidx.annotation.StringRes

sealed class Setting(
    @StringRes val key: Int,
    @StringRes val title: Int
) {

    class ListSetting(
        key: Int,
        title: Int,
        @StringRes val value: Int,
        val options: List<Int>
    ) : Setting(key, title)
}