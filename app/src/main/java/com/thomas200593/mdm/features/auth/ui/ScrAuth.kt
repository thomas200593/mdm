package com.thomas200593.mdm.features.auth.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thomas200593.mdm.R
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.ui.common.Theme.AppTheme
import com.thomas200593.mdm.core.ui.component.TxtMdBody
import com.thomas200593.mdm.features.conf.__contrast_accent.entity.ContrastAccent
import com.thomas200593.mdm.features.conf.__font_size.entity.FontSize

@Composable
fun ScrAuth() {
    ScreenContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent() = Scaffold(
    topBar = {
        SectionTopBar()
    },
    content = {
        SectionContent(paddingValues = it)
    },
    bottomBar = {
        SectionBottomBar()
    }
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SectionTopBar() {
    TopAppBar(
        title = {},
        actions = {
            IconButton(
                onClick = {/*TODO*/},
                content = { Icon(imageVector = Icons.Default.Settings, contentDescription = null) }
            )
        }
    )
}

@Composable
private fun SectionContent(paddingValues: PaddingValues) {
    Surface(
        modifier = Modifier.padding(paddingValues),
        content = {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(40.dp),
                content = {
                    item { Text("Panel Logo") }
                    item { Text("Panel Page Title") }
                    item { Text("Panel Page Auth Method") }
                }
            )
        }
    )
}

@Composable
private fun SectionBottomBar() {
    BottomAppBar (
        containerColor = MaterialTheme.colorScheme.surface,
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) { TxtMdBody(stringResource(R.string.app_name) + Constants.STR_APP_VERSION) }
        }
    )
}

@Composable
@Preview
private fun Preview() = AppTheme(
    darkThemeEnabled = false,
    dynamicColorEnabled = false,
    contrastAccent = ContrastAccent.defaultValue,
    fontSize = FontSize.defaultValue,
    content = {
        ScrAuth()
    }
)