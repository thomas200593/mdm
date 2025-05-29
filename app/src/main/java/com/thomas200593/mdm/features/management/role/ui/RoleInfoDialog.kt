package com.thomas200593.mdm.features.management.role.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thomas200593.mdm.core.ui.component.dialog.BaseAlertDialog
import com.thomas200593.mdm.features.management.role.entity.RoleEntity

@Composable fun RoleInfoDialog(
    modifier: Modifier = Modifier, role: RoleEntity,
    onDismissRequest: () -> Unit
) = BaseAlertDialog(
    modifier = modifier.fillMaxWidth(),
    onDismissRequest = onDismissRequest,
    title = { Text(role.label) },
    text = {
        Text("TODO")
    }
)