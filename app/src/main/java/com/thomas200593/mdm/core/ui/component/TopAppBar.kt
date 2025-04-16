package com.thomas200593.mdm.core.ui.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestTopLevelAppBar(
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    @StringRes title: Int? = null,
    @DrawableRes navBtnIcon: Int,
    navBtnOnClick: () -> Unit = {},
    @DrawableRes actBtnIcon: Int,
    actBtnOnClick: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = { title?.let { Text(text = stringResource(id = title)) } },
        navigationIcon = {
            IconButton(
                onClick = navBtnOnClick,
                content = { Icon(
                    imageVector = ImageVector.vectorResource(navBtnIcon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                ) }
            )
        },
        actions = {
            IconButton(
                onClick = actBtnOnClick,
                content = { Icon(
                    imageVector = ImageVector.vectorResource(actBtnIcon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                ) }
            )
        },
        colors = colors
    )
}