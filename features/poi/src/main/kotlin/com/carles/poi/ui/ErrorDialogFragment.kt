package com.carles.poi.ui

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.carles.poi.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ErrorDialogFragment : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val message = requireArguments().getString(EXTRA_MESSAGE, getString(R.string.error_server_response))
        val retry = requireArguments().getBoolean(EXTRA_RETRY, false)

        val alertDialogBuilder = MaterialAlertDialogBuilder(requireActivity()).setMessage(message)
        if (retry) {
            isCancelable = false
            alertDialogBuilder.setPositiveButton(R.string.error_retry) { _, _ ->
                dismiss()
                setFragmentResult(REQUEST_CODE_RETRY, Bundle.EMPTY)
            }
        }
        return alertDialogBuilder.create()
    }

    companion object {
        const val REQUEST_CODE_RETRY = "request_code_retry"

        private const val EXTRA_MESSAGE = "extra_message"
        private const val EXTRA_RETRY = "extra_retry"
        fun getBundle(message: String? = null, retry: Boolean? = null) =
            bundleOf(EXTRA_MESSAGE to message, EXTRA_RETRY to retry)
    }
}