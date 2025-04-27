package com.thomas200593.mdm.core.ui.component.dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.thomas200593.mdm.R

@Composable fun ErrorDialog(
    onDismissRequest: () -> Unit,
    title: String = stringResource(R.string.str_error),
    message: String = stringResource(R.string.str_error),
    btnConfirmText: String = stringResource(R.string.str_back)
) {
    BaseAlertDialog(
        onDismissRequest = onDismissRequest,
        dialogType = DialogType.ERROR,
        icon = { Icon(Icons.Default.Close, contentDescription = null) },
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = { Button(onClick = onDismissRequest, content = { Text(btnConfirmText) }) },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    )
}