package com.thomas200593.mdm.core.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

enum class ContextDialog { INFORMATION, WARNING, ERROR, SUCCESS, CONFIRMATION }

@Composable
fun DialogAlert(
    showDialog: MutableState<Boolean>,
    contextDialog: ContextDialog = ContextDialog.INFORMATION
) {
    if(showDialog.value) {

    }
}