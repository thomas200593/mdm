package com.thomas200593.mdm.core.ui.component.dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PermDeviceInformation
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.thomas200593.mdm.R

@Composable
fun ScrInfoDialog(onDismissRequest: () -> Unit, title: String, description: String) {
    BaseAlertDialog(
        onDismissRequest = onDismissRequest, icon = { Icon(Icons.Default.PermDeviceInformation, null) },
        title = { Text(title) }, text = { Text(description) },
        confirmButton = { Button(onClick = onDismissRequest, content = { Text(stringResource(R.string.str_back)) }) }
    )
}