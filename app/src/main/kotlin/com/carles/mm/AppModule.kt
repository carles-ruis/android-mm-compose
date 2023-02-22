package com.carles.mm

import com.carles.common.Navigator
import org.koin.dsl.module

val appModule = module {
    scope<MainActivity> {
        scoped<Navigator> { (activity: MainActivity) -> Navigate(activity) }
    }
}