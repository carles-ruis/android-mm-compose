package com.carles.mm

import androidx.navigation.NavController
import com.carles.common.Navigate
import org.koin.dsl.module

val appModule = module {
    scope<MainActivity> {
        scoped<Navigate> { (navController: NavController) ->
            NavigateImpl(navController)
        }
    }
}