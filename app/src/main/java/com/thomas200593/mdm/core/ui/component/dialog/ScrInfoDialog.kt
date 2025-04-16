package com.thomas200593.mdm.core.ui.component.dialog

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PermDeviceInformation
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.thomas200593.mdm.R

@Composable
fun ScrInfoDialog(onDismissRequest: () -> Unit, @StringRes title: Int, @StringRes description: Int) {
    Dialog(
        onDismissRequest = onDismissRequest,
        icon = { Icon(Icons.Default.PermDeviceInformation, null) },
        title = { Text(stringResource(title)) },
        text = { Text(stringResource(description)) },
        confirmButton = { Button(onClick = onDismissRequest, content = { Text(stringResource(R.string.str_back)) }) }
    )
}