package com.carles.mm

import android.util.Log
import androidx.navigation.NavController
import com.carles.common.Navigate

class NavigateImpl(private val navController: NavController) : Navigate {

    override fun toMonsterDetail(id: Int) {
        safeNavigation {
            navController.navigate(NavGraphDirections.toMonsterDetail(extraId = id))
        }
    }

    override fun toErrorDialog(errorMessage: String?) {
        safeNavigation {
            navController.navigate(NavGraphDirections.toErrorDialog(errorMessage, true))
        }
    }

    private fun safeNavigation(navigation: () -> Unit) {
        try {
            navigation()
        } catch (e: IllegalArgumentException) {
            Log.e("NavigateImpl", e.localizedMessage ?: "IllegalArgumentException at navigation")
        } catch (e: IllegalStateException) {
            Log.e("NavigateImpl", e.localizedMessage ?: "IllegalStateException at navigation")
        }
    }
}
