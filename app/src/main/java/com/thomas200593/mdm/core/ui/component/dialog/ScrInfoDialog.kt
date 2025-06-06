package com.thomas200593.mdm.core.ui.component.dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PermDeviceInformation
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.thomas200593.mdm.R
import com.thomas200593.mdm.core.ui.component.text.UiText

@Composable fun ScrInfoDialog(
    onDismissRequest: () -> Unit, title: String, description: String
) = BaseAlertDialog(
    onDismissRequest = onDismissRequest, icon = { Icon(Icons.Default.PermDeviceInformation, null) },
    title = { UiText(title) }, text = { UiText(description) },
    confirmButton = { Button(onClick = onDismissRequest, content = { UiText(stringResource(R.string.str_back)) }) }
)