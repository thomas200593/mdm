package com.thomas200593.mdm.core.ui.component.bottom_bar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable fun UiBottomBar(
    modifier: Modifier = Modifier,
    content: @Composable (RowScope.() -> Unit),
    containerColor : Color = BottomAppBarDefaults.containerColor,
    contentColor : Color = contentColorFor(containerColor),
    tonalElevation : Dp = BottomAppBarDefaults.ContainerElevation,
    contentPadding : PaddingValues = BottomAppBarDefaults.ContentPadding,
    windowInsets : WindowInsets = BottomAppBarDefaults.windowInsets
) = BottomAppBar(
    modifier = modifier,
    content = content,
    containerColor = containerColor,
    contentColor = contentColor,
    tonalElevation = tonalElevation,
    contentPadding = contentPadding,
    windowInsets = windowInsets
)