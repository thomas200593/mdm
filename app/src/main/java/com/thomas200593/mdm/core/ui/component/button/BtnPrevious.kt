package com.thomas200593.mdm.core.ui.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateBefore
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import com.thomas200593.mdm.core.ui.component.text.UiText

@Composable fun BtnPrevious(
    modifier: Modifier = Modifier, onClick: () -> Unit,
    icon: ImageVector = Icons.AutoMirrored.Default.NavigateBefore, label: String, enabled: Boolean = true,
    shape: Shape = ButtonDefaults.textShape, colors: ButtonColors = ButtonDefaults.textButtonColors(),
    elevation: ButtonElevation? = null, border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding, interactionSource: MutableInteractionSource? = null
) = TextButton (
    modifier = modifier, onClick = onClick, enabled = enabled, shape = shape, colors = colors,
    elevation = elevation, border = border, contentPadding = contentPadding, interactionSource = interactionSource,
    content = { Icon(imageVector = icon, contentDescription = null); UiText(label) }
)