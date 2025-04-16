package com.thomas200593.mdm.core.ui.component.dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.thomas200593.mdm.R

@Composable
fun SuccessDialog(
    onDismissRequest: () -> Unit,
    title: String = stringResource(R.string.str_success),
    message: String = stringResource(R.string.str_success),
    btnConfirmText: String = stringResource(R.string.str_ok)
) {
    BaseAlertDialog(
        onDismissRequest = onDismissRequest,
        dialogType = DialogType.SUCCESS,
        icon = { Icon(Icons.Default.Check, contentDescription = null) },
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = { Button(onClick = onDismissRequest) { Text(btnConfirmText) } },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    )
}