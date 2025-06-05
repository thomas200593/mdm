package com.thomas200593.mdm.core.ui.component.top_bar

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.thomas200593.mdm.core.ui.common.AppIcons

@OptIn(ExperimentalMaterial3Api::class) @Composable fun DestTopLevelAppBar(
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    @StringRes title: Int? = null,
    navBtnIcon: AppIcons.DestTopLevel,
    navBtnOnClick: () -> Unit = {},
    actBtnOnClick: () -> Unit = {}
) {
    UiTopBar(
        modifier = modifier, title = { title?.let { Text(text = stringResource(id = title)) } },
        type = TopAppBarType.Default, navigationIcon = { IconButton(
            onClick = navBtnOnClick,
            content = { Icon(
                imageVector = AppIcons.mapTopLevelToMaterialIcons(navBtnIcon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            ) }
        ) },
        actions = {
            IconButton(
                onClick = actBtnOnClick,
                content = { Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                ) }
            )
        },
        colors = colors
    )
}