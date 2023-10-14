package com.carles.mm.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.carles.common.R
import com.carles.common.ui.navigation.NavigationArguments

sealed class Screen(
    @StringRes val label: Int,
    private val path: String,
    val arguments: List<NamedNavArgument> = emptyList(),
    val menuItems: List<DestinationItem> = emptyList()
) {

    val route = buildString {
        append(path)
        arguments.forEach {
            append("/{")
            append(it.name)
            append("}")
        }
    }

    fun navigationRoute(vararg arguments: String) = buildString {
        append(path)
        arguments.forEach { arg ->
            append("/")
            append(arg)
        }
    }

    object Monsters : Screen(
        label = R.string.appname,
        path = "monsters_path",
        menuItems = listOf(
            DestinationItem(Icons.Filled.Settings, R.string.settings, Destination.Settings)
        )
    )

    object MonsterDetail : Screen(
        label = R.string.appname,
        path = "monster_detail_path",
        arguments = listOf(navArgument(NavigationArguments.monsterId) { NavType.StringType })
    )

    object Settings : Screen(
        label = R.string.settings,
        path = "settings_path"
    )
}

val screens = listOf(Screen.Monsters, Screen.MonsterDetail, Screen.Settings)
