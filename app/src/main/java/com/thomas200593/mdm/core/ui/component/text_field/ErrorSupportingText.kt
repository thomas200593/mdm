package com.thomas200593.mdm.core.ui.component.text_field

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import com.thomas200593.mdm.core.design_system.util.Constants

@Composable
fun ErrorSupportingText(errorTexts: List<String>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Constants.Dimens.dp8),
        content = { errorTexts.forEach { text -> key(text) { Text(text) } } }
    )
}