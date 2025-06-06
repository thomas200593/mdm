package com.thomas200593.mdm.core.ui.component.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import com.thomas200593.mdm.core.design_system.util.Constants

@Composable fun ThreeColumnsCard(
    modifier: Modifier = Modifier,
    type: CardType = CardType.Default,
    title: (@Composable () -> Unit)? = null,
    content: (@Composable () -> Unit),
    footer: (@Composable () -> Unit)? = null,
    shape: Shape = MaterialTheme.shapes.extraSmall,
    border: BorderStroke? = null,
    colors: CardColors? = null,
    elevation: CardElevation? = null
) = UiCard (
    modifier = modifier,
    type = type,
    shape = shape,
    border = border,
    colors = colors,
    elevation = elevation,
    content = { Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Constants.Dimens.dp8),
        content = { title?.invoke(); content.invoke(); footer?.invoke() }
    ) }
)

