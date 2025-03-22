package com.thomas200593.mdm.core.ui.component

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionHandler(
    permissions: List<String>,
    onPermissionsResult: (Map<String, Boolean>) -> Unit,
    content: @Composable (grantedPermissions: Map<String, Boolean>) -> Unit
) {
    /*TODO: Read from Datastore Preferences as JSON Array and try parse (make sure array not duplicate)*/
    val context = LocalContext.current
    val sharedPrefs = remember { context.getSharedPreferences("permissions_prefs", Context.MODE_PRIVATE) }
    val deniedPermissions = sharedPrefs.getStringSet("denied_permissions", emptySet()) ?: emptySet()
    val permissionsToRequest = permissions
        .filter { it !in deniedPermissions && ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED }
    val permissionState = rememberMultiplePermissionsState(
        permissions = permissionsToRequest,
        onPermissionsResult = { results ->
            sharedPrefs.edit().putStringSet("denied_permissions", results.filter { !it.value }.keys).apply()
            onPermissionsResult(results)
        }
    )
    val grantedPermissions = permissions
        .associateWith { ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED }
    val permanentlyDenied = permissionState.permissions
        .any { !it.status.isGranted && !it.status.shouldShowRationale }
    var showSettingsDialog by remember { mutableStateOf(false) }
    LaunchedEffect(
        key1 = permissionsToRequest,
        block = { if (permissionsToRequest.isNotEmpty()) { permissionState.launchMultiplePermissionRequest() } }
    )
    if (showSettingsDialog) {
        PermissionSettingsDialog(
            grantedPermissions = grantedPermissions,
            onDismiss = { showSettingsDialog = false },
            onOpenSettings = {
                showSettingsDialog = false
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    .apply { data = Uri.fromParts("package", context.packageName, null) }
                context.startActivity(intent)
            }
        )
    }
    when {
        grantedPermissions.values.all { it } -> content(grantedPermissions)
        permanentlyDenied -> showSettingsDialog = true
        else -> Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {
                Text(getRationaleMessage(permissionsToRequest))
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    content = { grantedPermissions.forEach { (permission, isGranted) -> AnimatedPermissionStatus(permission, isGranted) } }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { permissionState.launchMultiplePermissionRequest() },
                    content = { Text("Grant Remaining Permissions") }
                )
            }
        )
    }
}

@Composable
fun PermissionSettingsDialog(
    grantedPermissions: Map<String, Boolean>,
    onDismiss: () -> Unit,
    onOpenSettings: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Permissions Required", fontWeight = FontWeight.Bold) },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text("Some permissions are permanently denied. Please enable them in settings.")
                Spacer(modifier = Modifier.height(8.dp))
                grantedPermissions.forEach { (permission, isGranted) ->
                    Text(
                        text = if (isGranted) "✅ $permission granted" else "❌ $permission missing",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        },
        confirmButton = { Button(onClick = onOpenSettings, content = { Text("Open Settings") }) },
        dismissButton = { Button(onClick = onDismiss, content = { Text("Cancel") }) }
    )
}

fun getRationaleMessage(missingPermissions: List<String>): String {
    return when {
        Manifest.permission.CAMERA in missingPermissions -> "📷 Camera access is needed to take photos."
        Manifest.permission.READ_EXTERNAL_STORAGE in missingPermissions -> "🗂 Storage access is required to load media."
        Manifest.permission.POST_NOTIFICATIONS in missingPermissions -> "🔔 Notifications permission is needed to alert you."
        else -> "This app requires permissions to function properly."
    }
}

@Composable
fun AnimatedPermissionStatus(permission: String, isGranted: Boolean) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = isGranted, block = { visible = true })
    AnimatedVisibility (visible) {
        Text(
            text = if (isGranted) "✅ $permission granted" else "❌ $permission missing",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(4.dp)
        )
    }
}