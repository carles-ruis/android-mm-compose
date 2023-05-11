package com.carles.mm.ui

import androidx.navigation.NavHostController
import com.carles.common.ui.navigation.Destination
import com.carles.common.ui.navigation.Navigate
import com.carles.common.ui.navigation.Screen
import javax.inject.Inject

class NavigateImpl @Inject constructor() : Navigate {

    override lateinit var navController: NavHostController

    override fun to(destination: Destination) {
        when (destination) {
            Destination.Back -> up()
            Destination.Settings -> toSettings()
            is Destination.MonsterDetail -> toMonsterDetail(destination.monsterId.toString())
        }
    }

    private fun toSettings() {
        navController.navigate(Screen.Settings.navigationRoute())
    }

    private fun toMonsterDetail(id: String) {
        navController.navigate(Screen.MonsterDetail.navigationRoute(id))
    }

    private fun up() {
        navController.popBackStack()
    }
}