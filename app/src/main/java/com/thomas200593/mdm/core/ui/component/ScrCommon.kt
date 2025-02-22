package com.thomas200593.mdm.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.thomas200593.mdm.BuildConfig
import com.thomas200593.mdm.R

@Composable
fun ScrLoading(modifier: Modifier = Modifier) {
    Surface {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            TxtLgTitle(text = stringResource(R.string.app_name))
            CircularProgressIndicator()
            TxtLgTitle(text = "Loading")
            TxtMdTitle(text = "Ver: ${BuildConfig.VERSION_CODE}-${BuildConfig.VERSION_NAME}")
        }
    }
}