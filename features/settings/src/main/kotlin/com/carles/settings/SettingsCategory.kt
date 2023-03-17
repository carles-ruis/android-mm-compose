package com.carles.settings

import androidx.annotation.StringRes

typealias SettingsUi = List<SettingsCategory>

data class SettingsCategory(
    @StringRes val title: Int,
    val settings: List<Setting>
)
