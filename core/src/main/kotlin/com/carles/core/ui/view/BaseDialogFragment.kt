package com.carles.core.ui.view

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.navigation.fragment.findNavController
import com.carles.core.ui.view.BaseFragment.Companion.EXTRA_RESULT_CODE
import com.carles.core.ui.view.BaseFragment.Companion.NAVIGATION_RESULT

open class BaseDialogFragment : AppCompatDialogFragment() {

    protected fun navigateBackWithResult(result: Bundle = Bundle(), resultCode: Int = Activity.RESULT_OK) {
        dismiss()
        result.apply {
            putInt(EXTRA_RESULT_CODE, resultCode)
        }
        findNavController().previousBackStackEntry?.savedStateHandle?.set(NAVIGATION_RESULT, result)
    }
}