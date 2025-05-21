package com.thomas200593.mdm.core.ui.component.checkbox

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import com.thomas200593.mdm.core.design_system.util.Constants

@Composable
fun HorizontalCheckbox(modifier: Modifier = Modifier, enabled: Boolean, checked: Boolean, onCheckedChange: (Boolean) -> Unit, annotatedText: AnnotatedString) = Row(
    modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(Constants.Dimens.dp8), verticalAlignment = Alignment.CenterVertically,
    content = {
        Checkbox(
            modifier = Modifier.wrapContentWidth(), enabled = enabled, checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text(modifier = Modifier.weight(1f), text = annotatedText)
    }
)