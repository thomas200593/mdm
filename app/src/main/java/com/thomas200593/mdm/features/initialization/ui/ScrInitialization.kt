package com.thomas200593.mdm.features.initialization.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.ui.common.Theme.AppTheme
import com.thomas200593.mdm.core.ui.component.BtnConfLang
import com.thomas200593.mdm.core.ui.component.TxtLgTitle
import com.thomas200593.mdm.core.ui.component.TxtMdBody
import com.thomas200593.mdm.core.ui.component.text_field.TxtFieldEmail
import com.thomas200593.mdm.core.ui.component.text_field.TxtFieldPassword
import com.thomas200593.mdm.features.conf.__contrast_accent.entity.ContrastAccent
import com.thomas200593.mdm.features.conf.__font_size.entity.FontSize

@Composable
fun ScrInitialization() {
    ScreenContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent() = Scaffold(
    topBar = { SectionTopBar() },
    content = { SectionContent(paddingValues = it) },
    bottomBar = { SectionBottomBar() }
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SectionTopBar() {
    TopAppBar(
        title = {},
        actions = {
            BtnConfLang(onClick = {/*TODO*/}, border = null)
            IconButton(onClick = {/*TODO*/}, content = { Icon(imageVector = Icons.Default.Info, contentDescription = null) })
        }
    )
}

@Composable
private fun SectionContent(paddingValues: PaddingValues) {
    Surface(
        modifier = Modifier.padding(paddingValues),
        content = {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(Constants.Dimens.dp16),
                verticalArrangement = Arrangement.spacedBy(Constants.Dimens.dp16),
                content = {
                    /* Welcome Message */
                    item { PartTitle() }
                    /* Form */
                    item { PartForm() }
                }
            )
        }
    )
}

@Composable
private fun PartTitle() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        content = {
            Row(
                modifier = Modifier.weight(0.1f),
                content = { Icon(imageVector = Icons.Default.Checklist, contentDescription = null) }
            )
            Row(
                modifier = Modifier.weight(0.9f),
                content = { TxtLgTitle("First thing first, set up your admin account.") }
            )
        }
    )
}

@Composable
private fun PartForm() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraSmall,
        content = {
            Column(
                modifier = Modifier.padding(Constants.Dimens.dp16),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                content =  {
                    TxtFieldEmail()
                    TxtFieldPassword()
                    PartTnC()
                }
            )
        }
    )
}

@Composable
private fun PartTnC() {
    Row(
        modifier = Modifier.fillMaxWidth(1.0f),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Row (
                modifier = Modifier.weight(0.1f),
                content = {
                    Checkbox(
                        checked = false /*TODO*/,
                        onCheckedChange = {/*TODO*/}
                    )
                }
            )
            Row (
                modifier = Modifier.weight(0.9f),
                content = { TxtMdBody("I agree the terms of conditions and privacy policy") }
            )
        }
    )
}

@Composable
private fun SectionBottomBar() {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surface,
        content = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(Constants.Dimens.dp8),
                content = {
                    Button (
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.extraSmall,
                        colors = ButtonDefaults.textButtonColors().copy(containerColor = MaterialTheme.colorScheme.primaryContainer),
                        onClick = {/*TODO*/},
                        content = { TxtLgTitle("Proceed"/*TODO*/, color = MaterialTheme.colorScheme.onPrimaryContainer) }
                    )
                }
            )
        }
    )
}

@Composable
@Preview
private fun Preview() = AppTheme(
    darkThemeEnabled = true,
    dynamicColorEnabled = false,
    contrastAccent = ContrastAccent.defaultValue,
    fontSize = FontSize.defaultValue,
    content = { ScreenContent() }
)