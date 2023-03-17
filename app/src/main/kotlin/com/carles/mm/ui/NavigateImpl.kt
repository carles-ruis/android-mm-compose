package com.carles.mm.ui

import androidx.navigation.NavHostController
import com.carles.common.ui.navigation.Navigate
import com.carles.common.ui.navigation.Screen
import javax.inject.Inject

class NavigateImpl @Inject constructor() : Navigate {

    override lateinit var navController: NavHostController

    override fun toSettings() {
        navController.navigate(Screen.Settings.navigationRoute())
    }

    override fun toMonsterDetail(id: Int) {
        navController.navigate(Screen.MonsterDetail.navigationRoute(id.toString()))
    }

    override fun up() {
        navController.navigateUp()
    }
}