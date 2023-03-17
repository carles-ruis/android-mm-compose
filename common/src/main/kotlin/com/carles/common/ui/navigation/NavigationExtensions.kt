package com.carles.common.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.defaultComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        arguments = arguments,
        enterTransition = { slideInFromRightToLeft() },
        exitTransition = { slideOutFromRightToLeft() },
        popEnterTransition = { slideInFromLeftToRight() },
        popExitTransition = { slideOutFromLeftToRight() }
    ) { backStackEntry ->
        content(backStackEntry)
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun AnimatedContentScope<NavBackStackEntry>.slideInFromRightToLeft() = slideInHorizontally(initialOffsetX = { it })

@OptIn(ExperimentalAnimationApi::class)
private fun AnimatedContentScope<NavBackStackEntry>.slideOutFromRightToLeft() = slideOutHorizontally(targetOffsetX = { -it })

@OptIn(ExperimentalAnimationApi::class)
private fun AnimatedContentScope<NavBackStackEntry>.slideInFromLeftToRight() = slideInHorizontally(initialOffsetX = { -it })

@OptIn(ExperimentalAnimationApi::class)
private fun AnimatedContentScope<NavBackStackEntry>.slideOutFromLeftToRight() = slideOutHorizontally(targetOffsetX = { it })