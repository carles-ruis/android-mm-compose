package com.carles.core.ui.view

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.carles.core.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.view_progress.*

abstract class BaseActivity : AppCompatActivity() {

    protected var alertDialog: AlertDialog? = null
    protected abstract val layoutResourceId: Int
    protected abstract fun initViews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_in_from_right_to_left, R.anim.slide_out_from_right_to_left)
        setContentView(layoutResourceId)
        initViews()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_from_left_to_right, R.anim.slide_out_from_left_to_right)
    }

    protected fun showProgress() {
        progress?.visibility = VISIBLE
    }

    protected fun hideProgress() {
        progress?.visibility = GONE
    }

    protected fun showError(message: String?, onRetry: (() -> Unit)? = null) {
        hideProgress()
        if (isFinishing) return

        val isShowing = alertDialog?.isShowing ?: false
        if (!isShowing) {
            val alertDialogBuilder = MaterialAlertDialogBuilder(this).setMessage(message)
            if (onRetry != null) {
                alertDialogBuilder.setCancelable(false).setPositiveButton(R.string.error_retry) { _, _ -> onRetry() }
            }
            alertDialogBuilder.create().show()
        }
    }
}