package com.carles.mm

import android.util.Log
import androidx.navigation.findNavController
import com.carles.core.Navigator
import com.carles.poi.ui.ErrorDialogFragment
import com.carles.poi.ui.PoiDetailFragment

class Navigate(private val activity: MainActivity) : Navigator {

    private val navController by lazy { activity.findNavController(R.id.fragment_nav_host) }

    override fun up() {
        navController.navigateUp()
    }

    override fun toPoiDetailFromPoiList(id: String) {
        safeNavigation {
            navController.navigate(R.id.action_poiListFragment_to_poiDetailFragment, PoiDetailFragment.getBundle(id))
        }
    }

    override fun toSettings() {
        safeNavigation {
            navController.navigate(R.id.action_poiListFragment_to_appPreferencesFragment)
        }
    }

    override fun toErrorFromPoiList(errorMessage: String?) {
        safeNavigation {
            navController.navigate(
                R.id.action_poiListFragment_to_errorDialogFragment,
                ErrorDialogFragment.getBundle(errorMessage, true)
            )
        }
    }

    override fun toErrorFromPoiDetail(errorMessage: String?) {
        safeNavigation {
            navController.navigate(
                R.id.action_poiDetailFragment_to_errorDialogFragment,
                ErrorDialogFragment.getBundle(errorMessage, true)
            )
        }
    }

    private fun safeNavigation(navigation: () -> Unit) {
        try {
            navigation()
        } catch (e: IllegalArgumentException) {
            Log.e(Navigate::class.simpleName, e.localizedMessage ?: "navigation unknown")
        }
    }
}
