package com.thomas200593.mdm.core.ui.component.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.thomas200593.mdm.R
import com.thomas200593.mdm.core.design_system.util.Constants.Dimens.dp16
import com.thomas200593.mdm.core.design_system.util.Constants.STR_APP_VERSION
import com.thomas200593.mdm.core.ui.component.text.TextType
import com.thomas200593.mdm.core.ui.component.text.UiText

@Composable fun UiLoading(modifier: Modifier = Modifier, type: LoadingType, message: String = stringResource(R.string.str_loading)) = when (type) {
    is LoadingType.Dialog -> Dialog (
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false, usePlatformDefaultWidth = false),
        onDismissRequest = {}, content = { Box(
            modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)).clickable(enabled = false, onClick = {}),
            contentAlignment = Alignment.Center, content = { Column(
                horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp),
                content = {
                    CircularProgressIndicator()
                    UiText(text = message, color = Color.White, type = TextType.BODY_MD)
                }
            ) }
        ) }
    )
    is LoadingType.InnerCircularProgressIndicator -> Column(
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth(), content = {
            CircularProgressIndicator()
            UiText(text = message)
        }
    )
    is LoadingType.Screen -> Scaffold(content = { Column (
        modifier = modifier.padding(it).fillMaxSize().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(dp16, Alignment.CenterVertically),
        content = {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            UiText(text = message, type = TextType.TITLE_LG)
        }
    ) }, bottomBar = { Column(
        modifier = modifier.fillMaxWidth().navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(dp16, Alignment.CenterVertically),
        content = {
            UiText(text = stringResource(R.string.app_name), type = TextType.TITLE_LG)
            UiText(text = stringResource(R.string.str_version) + STR_APP_VERSION, type = TextType.TITLE_MD)
        }
    ) } )
}