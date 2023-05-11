package com.carles.common.ui.navigation

import androidx.navigation.NavHostController

interface Navigate {

    var navController: NavHostController

    fun to(destination: Destination)
}