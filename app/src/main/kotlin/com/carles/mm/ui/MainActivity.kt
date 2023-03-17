package com.carles.mm.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.currentBackStackEntryAsState
import com.carles.common.ui.GreenTheme
import com.carles.common.ui.navigation.Navigate
import com.carles.common.ui.navigation.Screen
import com.carles.common.ui.navigation.screens
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    @Inject
    lateinit var navigate: Navigate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GreenTheme {
                MainApp()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
    @Composable
    private fun MainApp() {
        val context = LocalContext.current

        val navController = rememberAnimatedNavController()
        navigate.navController = navController
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentScreen = screens.find { it.route == currentBackStackEntry?.destination?.route } ?: Screen.Monsters

        val showBackButton by remember(currentBackStackEntry) {
            derivedStateOf {
                navController.previousBackStackEntry != null
            }
        }

        var topBarTitle by remember(currentScreen) {
            mutableStateOf(context.getString(currentScreen.label))
        }
        val changeTitle: (String) -> Unit = { topBarTitle = it }

        Scaffold(
            topBar = {
                TopBar(navigate, topBarTitle, showBackButton, currentScreen.menuItems)
            }) { innerPadding ->
            MainNavHost(navigate, changeTitle, Modifier.padding(innerPadding))
        }
    }
}