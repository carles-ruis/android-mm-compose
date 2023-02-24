package com.carles.mm

import com.carles.common.Navigate
import org.koin.dsl.module

val appModule = module {
    scope<MainActivity> {
        scoped<Navigate> { (activity: MainActivity) -> NavigateImpl(activity) }
    }
}