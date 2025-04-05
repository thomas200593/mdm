package com.thomas200593.mdm.core.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import com.thomas200593.mdm.core.design_system.util.Constants

@Composable
fun PanelCard(
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit)? = null,
    content: (@Composable () -> Unit),
    footer: (@Composable () -> Unit)? = null,
    border: BorderStroke? = null,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.cardColors(),
    elevation: CardElevation = CardDefaults.cardElevation()
) {
    Card(
        modifier = modifier,
        border = border,
        shape = shape,
        colors = colors,
        elevation = elevation,
        content = {
            Column (
                modifier = Modifier.fillMaxWidth().padding(Constants.Dimens.dp16),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Constants.Dimens.dp16, Alignment.CenterVertically),
                content = { title?.invoke(); content.invoke(); footer?.invoke() }
            )
        }
    )
}