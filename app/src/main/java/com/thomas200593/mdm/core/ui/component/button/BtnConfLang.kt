package com.thomas200593.mdm.core.ui.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.ui.component.TxtMdLabel
import com.thomas200593.mdm.features.conf.__language.entity.Language

@OptIn(ExperimentalMaterial3Api::class) @Composable fun BtnConfLang(
    modifier: Modifier = Modifier,
    languages: List<Language>, languageIcon: String, languageName: String? = null, onSelectLanguage: (Language) -> Unit,
    enabled: Boolean = true, shape: Shape = MaterialTheme.shapes.medium, colors: ButtonColors = ButtonDefaults.textButtonColors(),
    elevation: ButtonElevation? = null, border: BorderStroke? = null, contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    interactionSource: MutableInteractionSource? = null
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        content = {
            TextButton(
                modifier = modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true),
                onClick = { expanded = true }, enabled = enabled, shape = shape, colors = colors,
                elevation = elevation, border = border, contentPadding = contentPadding, interactionSource = interactionSource
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Constants.Dimens.dp8),
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        TxtMdLabel(text = languageIcon)
                        languageName?.let { TxtMdLabel(text = it, color = colors.contentColor, maxLines = 1) }
                    }
                )
            }
            ExposedDropdownMenu(
                expanded = expanded, onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.5f), content = {
                    languages.forEach {
                        DropdownMenuItem(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { expanded = false; onSelectLanguage.invoke(it) },
                            leadingIcon = { Text(it.country.flag) },
                            text = { TxtMdLabel(text = it.country.name) },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            )
        }
    )
}