package com.carles.mm

import com.carles.core.Navigator
import org.koin.dsl.module

val appModule = module {
    scope<MainActivity> {
        scoped<Navigator> { (activity: MainActivity) -> Navigate(activity) }
    }
}