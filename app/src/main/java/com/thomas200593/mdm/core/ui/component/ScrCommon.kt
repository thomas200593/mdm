package com.thomas200593.mdm.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thomas200593.mdm.BuildConfig
import com.thomas200593.mdm.R
import com.thomas200593.mdm.core.ui.common.Theme
import com.thomas200593.mdm.features.conf.__contrast_accent.entity.ContrastAccent
import com.thomas200593.mdm.features.conf.__font_size.entity.FontSize

@Composable
fun ScrLoading(modifier: Modifier = Modifier) {
    Surface {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // The loading section takes up available space in the center
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
            ) {
                CircularProgressIndicator()
                TxtLgTitle(text = "Loading")
            }

            Column(
                modifier = Modifier.wrapContentHeight(Alignment.Bottom),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TxtLgTitle(text = stringResource(R.string.app_name))
                TxtMdTitle(text = "Ver: ${BuildConfig.VERSION_CODE}-${BuildConfig.VERSION_NAME}")
            }
        }
    }
}

@Composable
@Preview
private fun PreviewScrLoading() = Theme.AppTheme(
    darkThemeEnabled = true,
    dynamicColorEnabled = false,
    contrastAccent = ContrastAccent.defaultValue,
    fontSize = FontSize.defaultValue,
    content = {
        ScrLoading()
    }
)