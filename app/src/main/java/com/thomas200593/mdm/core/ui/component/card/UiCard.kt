package com.thomas200593.mdm.core.ui.component.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape

@Composable fun UiCard(
    modifier: Modifier = Modifier,
    type: CardType = CardType.Default,
    shape: Shape? = null,
    colors: CardColors? = null,
    elevation: CardElevation? = null,
    border: BorderStroke? = null,
    content: @Composable ColumnScope.() -> Unit
) = when(type) {
    CardType.Default -> Card(
        modifier = modifier,
        shape = shape ?: CardDefaults.shape,
        colors = colors ?: CardDefaults.cardColors(),
        elevation = elevation ?: CardDefaults.cardElevation(),
        border = border,
        content = content
    )
    CardType.Elevated -> ElevatedCard(
        modifier = modifier,
        shape = shape ?: CardDefaults.elevatedShape,
        colors = colors ?: CardDefaults.elevatedCardColors(),
        elevation = elevation ?: CardDefaults.elevatedCardElevation(),
        content = content
    )
    CardType.Outlined -> OutlinedCard(
        modifier = modifier,
        shape = shape ?: CardDefaults.outlinedShape,
        colors = colors ?: CardDefaults.outlinedCardColors(),
        elevation = elevation ?: CardDefaults.outlinedCardElevation(),
        border = border ?: CardDefaults.outlinedCardBorder(),
        content = content
    )
}