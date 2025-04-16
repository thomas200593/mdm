package com.thomas200593.mdm.core.ui.component.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.DialogProperties

enum class DialogType {
    CONFIRMATION, ERROR, INFORMATION, SUCCESS, WARNING;
    companion object { val defaultValue = INFORMATION }
}
@Composable
fun BaseAlertDialog (
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    dialogType: DialogType = DialogType.defaultValue,
    shape: Shape = AlertDialogDefaults.shape,
    tonalElevation: Dp = AlertDialogDefaults.TonalElevation,
    containerColor: Color? = null,
    icon: @Composable (() -> Unit)? = null,
    iconContentColor: Color? = null,
    title: @Composable (() -> Unit)? = null,
    titleContentColor: Color? = null,
    text: @Composable (() -> Unit)? = null,
    textContentColor: Color? = null,
    confirmButton: @Composable (() -> Unit)? = null,
    dismissButton:  @Composable (() -> Unit)? = null,
    properties: DialogProperties = DialogProperties()
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        confirmButton = { confirmButton?.invoke() },
        dismissButton = { dismissButton?.invoke() },
        icon = icon,
        title = title,
        text = text,
        shape = shape,
        containerColor = containerColor ?: getContainerColor(dialogType),
        iconContentColor = iconContentColor ?: getContentColor(dialogType),
        titleContentColor = titleContentColor ?: getContentColor(dialogType),
        textContentColor = textContentColor ?: getContentColor(dialogType),
        tonalElevation = tonalElevation,
        properties = properties,
    )
}
@Composable
private fun getContainerColor(dialogType: DialogType) = when(dialogType) {
    DialogType.CONFIRMATION -> MaterialTheme.colorScheme.secondaryContainer
    DialogType.ERROR -> MaterialTheme.colorScheme.errorContainer
    DialogType.INFORMATION -> MaterialTheme.colorScheme.primaryContainer
    DialogType.SUCCESS -> MaterialTheme.colorScheme.tertiaryContainer
    DialogType.WARNING -> MaterialTheme.colorScheme.errorContainer
}
@Composable
private fun getContentColor(dialogType: DialogType) = when (dialogType) {
    DialogType.CONFIRMATION -> MaterialTheme.colorScheme.onSecondaryContainer
    DialogType.ERROR -> MaterialTheme.colorScheme.onErrorContainer
    DialogType.INFORMATION -> MaterialTheme.colorScheme.onPrimaryContainer
    DialogType.SUCCESS -> MaterialTheme.colorScheme.onTertiaryContainer
    DialogType.WARNING -> MaterialTheme.colorScheme.onErrorContainer
}