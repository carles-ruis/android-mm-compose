package com.carles.settings.ui

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.carles.settings.R
import com.carles.settings.Setting
import com.carles.settings.SettingsCategory
import com.carles.settings.SettingsUi

@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val uiState: SettingsUiState by viewModel.uiState.collectAsStateWithLifecycle()
    SettingsScreen(uiState) { key, selectedOption -> viewModel.onSettingSelected(key, selectedOption) }
}

@Composable
fun SettingsScreen(uiState: SettingsUiState, context: Context = LocalContext.current, onSelected: (Int, Int) -> Unit) {
    uiState.error?.let {
        Toast.makeText(context, uiState.error, Toast.LENGTH_LONG).show()
    }
    uiState.settings?.let {
        SettingsContent(it, onSelected)
    }
}

@Composable
fun SettingsContent(settings: SettingsUi, onSelected: (Int, Int) -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(top = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        settings.forEach { category ->
            SettingsCategory(category) { key, selectedOption -> onSelected(key, selectedOption) }
        }
    }
}

@Composable
fun SettingsCategory(category: SettingsCategory, modifier: Modifier = Modifier, onSelected: (Int, Int) -> Unit) {
    Column(modifier) {
        Text(
            text = stringResource(category.title),
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp, start = 72.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.tertiary
        )
        category.settings.forEach { setting ->
            when (setting) {
                is Setting.ListSetting -> SettingList(setting) { key, selectedOption -> onSelected(key, selectedOption) }
            }
        }
    }
}

@Composable
fun SettingList(setting: Setting.ListSetting, modifier: Modifier = Modifier, onSelected: (Int, Int) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    Column(modifier
        .clickable { showDialog = true }
        .padding(top = 16.dp, bottom = 16.dp, start = 72.dp)) {
        Text(
            text = stringResource(setting.title),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.labelMedium,
        )
        Text(
            text = stringResource(setting.value),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    if (showDialog) {
        ListSettingDialog(
            setting = setting,
            onDismiss = { showDialog = false }
        ) { key, selectedOption -> onSelected(key, selectedOption) }
    }
}

@Composable
fun ListSettingDialog(
    setting: Setting.ListSetting,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    onSelected: (Int, Int) -> Unit
) {
    AlertDialog(
        modifier = modifier.testTag(stringResource(R.string.tag_settings_dialog)),
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onDismiss) { Text(stringResource(android.R.string.cancel)) }
        },
        title = {
            Text(
                text = stringResource(id = setting.title),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(Modifier.selectableGroup()) {
                setting.options.forEach { option ->
                    ListSettingDialogRow(setting.value, option) {
                        onSelected(setting.key, option)
                        onDismiss()
                    }
                }
            }
        })
}

@Composable
fun ListSettingDialogRow(
    currentSelection: Int,
    @StringRes option: Int,
    modifier: Modifier = Modifier,
    onSelected: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth()
            .selectable(
                selected = currentSelection == option,
                onClick = { onSelected() },
                role = Role.RadioButton
            )
    ) {
        RadioButton(
            selected = currentSelection == option,
            onClick = null
        )
        Text(
            text = stringResource(id = option),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}