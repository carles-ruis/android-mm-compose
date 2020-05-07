package com.carles.core.ui.view

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.view_progress.progress

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
}
