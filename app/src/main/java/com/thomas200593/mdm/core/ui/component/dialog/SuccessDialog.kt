package com.thomas200593.mdm.core.ui.component.dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.thomas200593.mdm.R
import com.thomas200593.mdm.core.ui.component.text.UiText

@Composable fun SuccessDialog(
    onDismissRequest: () -> Unit, title: String = stringResource(R.string.str_success), message: String = stringResource(R.string.str_success),
    btnConfirmText: String = stringResource(R.string.str_ok)
) = BaseAlertDialog(
    onDismissRequest = onDismissRequest,
    dialogType = DialogType.SUCCESS,
    icon = { Icon(Icons.Default.Check, contentDescription = null) },
    title = { UiText(title) },
    text = { UiText(message) },
    confirmButton = { Button(onClick = onDismissRequest) { UiText(btnConfirmText) } },
    properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
)