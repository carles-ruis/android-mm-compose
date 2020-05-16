package com.carles.mm

import com.carles.core.Navigator
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    scope(named<MainActivity>()) {
        scoped<Navigator> { (activity: MainActivity) -> Navigate(activity) }
    }
}