package com.carles.common.ui.navigation

sealed class Destination {
    object Back : Destination()
    object Settings : Destination()
    class MonsterDetail(val monsterId: Int) : Destination()
}
