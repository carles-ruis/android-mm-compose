package com.carles.core.ui.view

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.view_progress.*

abstract class BaseFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {

    protected fun showProgress() {
        progress?.visibility = VISIBLE
    }

    protected fun hideProgress() {
        progress?.visibility = GONE
    }

    protected fun initDefaultToolbar(toolbar: Toolbar): Toolbar {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        return toolbar
    }

    protected fun navigateForResult(@IdRes resId: Int, args: Bundle = Bundle(), requestCode: String) {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Bundle>(NAVIGATION_RESULT)
            ?.observe(viewLifecycleOwner) { result ->
                onNavigationResult(requestCode, result)
            }
        findNavController().navigate(resId, args)
    }

    protected fun navigateBackWithResult(result: Bundle = Bundle(), resultCode: Int = RESULT_OK) {
        result.apply {
            putInt(EXTRA_RESULT_CODE, resultCode)
        }
        findNavController().previousBackStackEntry?.savedStateHandle?.set(NAVIGATION_RESULT, result)
        findNavController().popBackStack()
    }

    protected open fun onNavigationResult(requestCode: String, result: Bundle) {
        // empty default implementation
    }

    companion object {
        const val NAVIGATION_RESULT = "livedata_key_navigation_result"
        const val EXTRA_RESULT_CODE = "extra_result_ok"
    }
}
