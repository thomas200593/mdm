package com.thomas200593.mdm.core.ui.component.top_bar

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.thomas200593.mdm.core.ui.component.text.UiText

@OptIn(ExperimentalMaterial3Api::class) @Composable fun DestTopLevelAppBar(
    modifier : Modifier = Modifier,
    colors : TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    @StringRes title : Int? = null,
    navigationIcon : @Composable (() -> Unit)? = null,
    actions : @Composable (RowScope.() -> Unit) = {}
) = UiTopBar(
    title = { title?.let { UiText(text = stringResource(id = title)) } },
    type = TopAppBarType.Default,
    modifier = modifier,
    navigationIcon = navigationIcon,
    actions = actions,
    colors = colors
)