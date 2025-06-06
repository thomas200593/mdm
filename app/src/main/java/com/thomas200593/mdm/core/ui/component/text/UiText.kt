package com.thomas200593.mdm.core.ui.component.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable fun UiText(
    text: String,
    type: TextType = TextType.defaultValue,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    minLines: Int = 1,
    maxLines: Int = Int.MAX_VALUE
) = UiText(
    text = AnnotatedString(text),
    type = type,
    modifier = modifier,
    color = color,
    textAlign = textAlign,
    overflow = overflow,
    minLines = minLines,
    maxLines = maxLines
)
@Composable fun UiText(
    text: AnnotatedString,
    type: TextType = TextType.defaultValue,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    minLines: Int = 1,
    maxLines: Int = Int.MAX_VALUE
) = Text(
    text = text,
    modifier = modifier,
    color = color,
    textAlign = textAlign,
    overflow = overflow,
    minLines = minLines,
    maxLines = maxLines,
    style = getTextStyle(type)
)
@Composable private fun getTextStyle(type: TextType): TextStyle = when (type) {
    TextType.TITLE_MD -> MaterialTheme.typography.titleMedium
    TextType.TITLE_LG -> MaterialTheme.typography.titleLarge
    TextType.LABEL_MD -> MaterialTheme.typography.labelMedium
    TextType.BODY_MD -> MaterialTheme.typography.bodyMedium
}