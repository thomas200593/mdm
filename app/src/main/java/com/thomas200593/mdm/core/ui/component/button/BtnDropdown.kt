package com.thomas200593.mdm.core.ui.component.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.ui.component.text.TextType
import com.thomas200593.mdm.core.ui.component.text.UiText

@OptIn(ExperimentalMaterial3Api::class) @Composable fun <T> BtnDropdown(
    modifier: Modifier = Modifier,
    items: List<T>,
    itemIcon: (T) -> SelectorIcon,
    selectedIcon: SelectorIcon,
    itemLabel: @Composable (T) -> Unit,
    selectedLabel: String? = null,
    onSelectItem: (T) -> Unit,
    enabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.extraSmall,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    interactionSource: MutableInteractionSource? = null
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded, onExpandedChange = { expanded = !expanded }, content = {
            TextButton(
                modifier = modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true),
                onClick = { expanded = true },
                enabled = enabled,
                shape = shape,
                colors = colors,
                elevation = elevation,
                border = border,
                contentPadding = contentPadding,
                interactionSource = interactionSource,
                content = { Row(
                    horizontalArrangement = Arrangement.spacedBy(Constants.Dimens.dp8),
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        RenderSelectorIcon(icon = selectedIcon)
                        selectedLabel?.let { UiText(text = it, type = TextType.LABEL_MD, maxLines = 1) }
                    }
                ) }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.5f), content = { items.forEach { item -> DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { expanded = false; onSelectItem(item) },
                    leadingIcon = { RenderSelectorIcon(icon = itemIcon(item)) },
                    text = { itemLabel(item) },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                ) } }
            )
        }
    )
}
@Composable
private fun RenderSelectorIcon(icon: SelectorIcon, iconSize: Dp = ButtonDefaults.IconSize) = when (icon) {
    is SelectorIcon.EmojiString -> UiText(text = icon.emoji, type = TextType.LABEL_MD, maxLines = 1)
    is SelectorIcon.DrawableAssets -> Icon(modifier = Modifier.size(iconSize),
        imageVector = ImageVector.vectorResource(icon.resId), contentDescription = null)
}
sealed class SelectorIcon {
    data class EmojiString(val emoji: String) : SelectorIcon()
    data class DrawableAssets(@DrawableRes val resId: Int) : SelectorIcon()
}