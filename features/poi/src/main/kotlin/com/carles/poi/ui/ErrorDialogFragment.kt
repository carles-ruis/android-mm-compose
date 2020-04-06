package com.carles.poi.ui

import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import com.carles.core.ui.view.BaseDialogFragment
import com.carles.mm.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ErrorDialogFragment : BaseDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val message = requireArguments().getString(EXTRA_MESSAGE, getString(R.string.error_server_response))
        val retry = requireArguments().getBoolean(EXTRA_RETRY, false)

        val alertDialogBuilder = MaterialAlertDialogBuilder(requireActivity()).setMessage(message)
        if (retry) {
            isCancelable = false
            alertDialogBuilder.setPositiveButton(R.string.error_retry) { _, _ ->
                navigateBackWithResult()
            }
        }
        return alertDialogBuilder.create()
    }

    companion object {
        private const val EXTRA_MESSAGE = "extra_message"
        private const val EXTRA_RETRY = "extra_retry"
        fun getBundle(message: String? = null, retry: Boolean? = null) =
            bundleOf(EXTRA_MESSAGE to message, EXTRA_RETRY to retry)
    }
}