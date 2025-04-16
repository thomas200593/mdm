package com.thomas200593.mdm.core.ui.component.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.thomas200593.mdm.R
import com.thomas200593.mdm.core.design_system.util.Constants.Dimens.dp16
import com.thomas200593.mdm.core.design_system.util.Constants.STR_APP_VERSION
import com.thomas200593.mdm.core.ui.component.TxtLgTitle
import com.thomas200593.mdm.core.ui.component.TxtMdTitle

@Composable
fun ScrLoading(@StringRes label: Int = R.string.str_loading) {
    Scaffold(
        content = {
            Column (
                modifier = Modifier.padding(it).fillMaxSize().verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dp16, Alignment.CenterVertically),
                content = {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    TxtLgTitle(text = stringResource(label))
                }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier.fillMaxWidth().navigationBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dp16, Alignment.CenterVertically),
                content = {
                    TxtLgTitle(text = stringResource(R.string.app_name))
                    TxtMdTitle(text = stringResource(R.string.str_version) + STR_APP_VERSION)
                }
            )
        }
    )
}