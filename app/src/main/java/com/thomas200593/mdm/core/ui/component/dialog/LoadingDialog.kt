package com.thomas200593.mdm.core.ui.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.thomas200593.mdm.R

@Composable
fun LoadingDialog(message: String = stringResource(R.string.str_loading)) {
    AlertDialog(
        onDismissRequest = {}, confirmButton = {},
        text = { Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            content = { CircularProgressIndicator(); Text(message) }
        ) },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    )
}