package com.thomas200593.mdm.core.ui.component.top_bar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class) @Composable fun UiTopBar(
    title : @Composable () -> Unit,
    type : TopAppBarType,
    modifier : Modifier = Modifier,
    navigationIcon : @Composable (() -> Unit)? = null,
    actions : @Composable RowScope.() -> Unit = {},
    windowInsets : WindowInsets = TopAppBarDefaults.windowInsets,
    colors : TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    scrollBehavior : TopAppBarScrollBehavior? = null
) = when (type) {
    TopAppBarType.Default -> TopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = { navigationIcon?.invoke() },
        actions = actions,
        expandedHeight = TopAppBarDefaults.TopAppBarExpandedHeight,
        windowInsets = windowInsets,
        colors = colors,
        scrollBehavior = scrollBehavior
    )
    TopAppBarType.CenterAligned -> CenterAlignedTopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = { navigationIcon?.invoke() },
        actions = actions,
        expandedHeight = TopAppBarDefaults.TopAppBarExpandedHeight,
        windowInsets = windowInsets,
        colors = colors,
        scrollBehavior = scrollBehavior
    )
    TopAppBarType.Medium -> MediumTopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = { navigationIcon?.invoke() },
        actions = actions,
        collapsedHeight = TopAppBarDefaults.MediumAppBarCollapsedHeight,
        expandedHeight = TopAppBarDefaults.MediumAppBarExpandedHeight,
        windowInsets = windowInsets,
        colors = colors,
        scrollBehavior = scrollBehavior
    )
    TopAppBarType.Large -> LargeTopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = { navigationIcon?.invoke() },
        actions = actions,
        collapsedHeight = TopAppBarDefaults.LargeAppBarCollapsedHeight,
        expandedHeight = TopAppBarDefaults.LargeAppBarExpandedHeight,
        windowInsets = windowInsets,
        colors = colors,
        scrollBehavior = scrollBehavior
    )
    TopAppBarType.MediumFlexible -> MediumTopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = { navigationIcon?.invoke() },
        actions = actions,
        collapsedHeight = TopAppBarDefaults.MediumAppBarCollapsedHeight,
        expandedHeight = TopAppBarDefaults.MediumAppBarExpandedHeight,
        windowInsets = windowInsets,
        colors = colors,
        scrollBehavior = scrollBehavior
    )
    TopAppBarType.LargeFlexible -> LargeTopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = { navigationIcon?.invoke() },
        actions = actions,
        collapsedHeight = TopAppBarDefaults.LargeAppBarCollapsedHeight,
        expandedHeight = TopAppBarDefaults.LargeAppBarExpandedHeight,
        windowInsets = windowInsets,
        colors = colors,
        scrollBehavior = scrollBehavior
    )
}