package com.thomas200593.mdm.core.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.ui.common.Theme
import com.thomas200593.mdm.features.conf.__contrast_accent.entity.ContrastAccent
import com.thomas200593.mdm.features.conf.__font_size.entity.FontSize

@Composable
fun BtnConfLang(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    languageIcon: String? = null,
    languageName: String? = null,
    enabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.medium,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults. TextButtonContentPadding,
    interactionSource: MutableInteractionSource? = null
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(Constants.Dimens.dp8),
                verticalAlignment = Alignment.CenterVertically
            ) {
                languageIcon?.let { Text(it) }
                languageName?.let { Text(it) }
            }
        }
    )
}

@Composable
@Preview
private fun TestBtnConfLang() = Theme.AppTheme(
    darkThemeEnabled = true,
    dynamicColorEnabled = true,
    contrastAccent = ContrastAccent.defaultValue,
    fontSize = FontSize.defaultValue
) {
    Surface  {
        Column(modifier = Modifier.fillMaxSize()) {
            BtnConfLang(
                languageIcon = String(Character.toChars(0x1F1EE))+String(Character.toChars(0x1F1E9)),
                languageName = "Indonesia",
                onClick = {}
            )
        }
    }
}