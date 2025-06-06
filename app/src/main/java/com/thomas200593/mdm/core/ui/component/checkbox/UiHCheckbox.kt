package com.thomas200593.mdm.core.ui.component.checkbox

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.ui.component.text.UiText

@Composable fun UiHCheckbox(
    modifier: Modifier = Modifier, enabled: Boolean, checked: Boolean, annotatedText: AnnotatedString,
    onCheckedChange: (Boolean) -> Unit
) = Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(Constants.Dimens.dp8),
    verticalAlignment = Alignment.CenterVertically,
    content = {
        Checkbox(modifier = Modifier.wrapContentWidth(), enabled = enabled, checked = checked, onCheckedChange = onCheckedChange)
        UiText(modifier = Modifier.weight(1f), text = annotatedText)
    }
)