package com.thomas200593.mdm.core.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable fun TxtLgTitle(
    text: String, modifier: Modifier = Modifier, color: Color = MaterialTheme.colorScheme.onSurface,
    textAlign: TextAlign = MaterialTheme.typography.titleLarge.textAlign,
    overflow: TextOverflow = TextOverflow.Clip, minLines: Int = 1, maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = MaterialTheme.typography.titleLarge
) {
    Text(
        text = text, modifier = modifier, color = color, textAlign = textAlign, overflow = overflow,
        minLines = minLines, maxLines = maxLines, style = style
    )
}
@Composable fun TxtMdTitle(
    text: String, modifier: Modifier = Modifier, color: Color = MaterialTheme.colorScheme.onSurface,
    textAlign: TextAlign = MaterialTheme.typography.titleMedium.textAlign,
    overflow: TextOverflow = TextOverflow.Clip, minLines: Int = 1, maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = MaterialTheme.typography.titleMedium
) {
    Text(
        text = text, modifier = modifier, color = color, textAlign = textAlign, overflow = overflow,
        minLines = minLines, maxLines = maxLines, style = style
    )
}
@Composable fun TxtMdLabel(
    text: String, modifier: Modifier = Modifier, color: Color = MaterialTheme.colorScheme.onSurface,
    textAlign: TextAlign = MaterialTheme.typography.labelMedium.textAlign,
    overflow: TextOverflow = TextOverflow.Clip, minLines: Int = 1, maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = MaterialTheme.typography.labelMedium
) {
    Text(
        text = text, modifier = modifier, color = color, textAlign = textAlign, overflow = overflow,
        minLines = minLines, maxLines = maxLines, style = style
    )
}
@Composable fun TxtMdBody(
    text: String, modifier: Modifier = Modifier, color: Color = MaterialTheme.colorScheme.onSurface,
    textAlign: TextAlign = MaterialTheme.typography.bodyMedium.textAlign,
    overflow: TextOverflow = TextOverflow.Clip, minLines: Int = 1, maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Text(
        text = text, modifier = modifier, color = color, textAlign = textAlign, overflow = overflow,
        minLines = minLines, maxLines = maxLines, style = style
    )
}