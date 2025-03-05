package com.thomas200593.mdm.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.thomas200593.mdm.R
import com.thomas200593.mdm.core.design_system.util.Constants.Dimens.dp16
import com.thomas200593.mdm.core.design_system.util.Constants.STR_APP_VERSION

@Composable
fun ScrLoading(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier.fillMaxSize().padding(dp16).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dp16, Alignment.CenterVertically)
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                TxtLgTitle(text = stringResource(R.string.str_loading))
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TxtLgTitle(text = stringResource(R.string.app_name))
                TxtMdTitle(text = stringResource(R.string.str_version) + STR_APP_VERSION)
            }
        }
    }
}

@Composable
fun CenteredCircularProgressIndicator() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) { CircularProgressIndicator() }
}