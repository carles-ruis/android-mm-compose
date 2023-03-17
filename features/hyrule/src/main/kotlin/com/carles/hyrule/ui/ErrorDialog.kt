package com.carles.hyrule.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.carles.hyrule.R

@Composable
fun ErrorDialog(message: String?, onRetryClick: () -> Unit, onDismiss: () -> Unit = {}) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onRetryClick) { Text(stringResource(R.string.error_retry)) }
        },
        text = {
            Text(
                text = message ?: stringResource(R.string.error_server_response),
                style = MaterialTheme.typography.bodyLarge
            )
        })
}
