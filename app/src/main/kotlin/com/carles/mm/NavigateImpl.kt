package com.carles.mm

import android.util.Log
import androidx.navigation.findNavController
import com.carles.common.Navigate
import com.carles.hyrule.ui.ErrorDialogFragment
import com.carles.hyrule.ui.MonsterDetailFragment

class NavigateImpl(private val activity: MainActivity) : Navigate {

    private val navController by lazy { activity.findNavController(R.id.fragment_nav_host) }

    override fun up() {
        navController.navigateUp()
    }

    override fun toMonsterDetailFromMonsters(id: Int) {
        safeNavigation {
            navController.navigate(R.id.to_monsterDetail_from_monsters, MonsterDetailFragment.getBundle(id))
        }
    }

    override fun toSettings() {
        safeNavigation {
            navController.navigate(R.id.to_settings_from_monsters)
        }
    }

    override fun toErrorFromMonsters(errorMessage: String?) {
        safeNavigation {
            navController.navigate(
                R.id.to_errorDialog_from_monsters,
                ErrorDialogFragment.getBundle(errorMessage, true)
            )
        }
    }

    override fun toErrorFromMonsterDetail(errorMessage: String?) {
        safeNavigation {
            navController.navigate(
                R.id.to_errorDialog_from_monsterDetail,
                ErrorDialogFragment.getBundle(errorMessage, true)
            )
        }
    }

    private fun safeNavigation(navigation: () -> Unit) {
        try {
            navigation()
        } catch (e: IllegalArgumentException) {
            Log.e("NavigateImpl", e.localizedMessage ?: "navigation unknown")
        }
    }
}
