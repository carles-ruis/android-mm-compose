package com.carles.mm

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.carles.common.Navigate
import javax.inject.Inject

class NavigateImpl @Inject constructor(private val activity: FragmentActivity) : Navigate {

    private val navController by lazy { activity.findNavController(R.id.fragment_nav_host) }

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
