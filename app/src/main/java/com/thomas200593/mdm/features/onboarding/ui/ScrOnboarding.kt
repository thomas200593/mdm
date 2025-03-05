package com.thomas200593.mdm.features.onboarding.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.thomas200593.mdm.R
import com.thomas200593.mdm.core.ui.common.Theme
import com.thomas200593.mdm.core.ui.component.BtnConfLang
import com.thomas200593.mdm.core.ui.component.BtnNext
import com.thomas200593.mdm.core.ui.component.BtnPrevious
import com.thomas200593.mdm.core.ui.component.ScrLoading
import com.thomas200593.mdm.core.ui.component.TxtLgTitle
import com.thomas200593.mdm.core.ui.component.TxtMdBody
import com.thomas200593.mdm.features.conf.__contrast_accent.entity.ContrastAccent
import com.thomas200593.mdm.features.conf.__country.entity.Country
import com.thomas200593.mdm.features.conf.__dynamic_color.entity.DynamicColor
import com.thomas200593.mdm.features.conf.__font_size.entity.FontSize
import com.thomas200593.mdm.features.conf.__language.entity.Language
import com.thomas200593.mdm.features.conf._localization.entity.Localization
import com.thomas200593.mdm.features.conf._ui.entity.UI
import com.thomas200593.mdm.features.conf.common.entity.Common
import com.thomas200593.mdm.features.initial.entity.FirstTimeStatus
import com.thomas200593.mdm.features.onboarding.entity.Onboarding
import com.thomas200593.mdm.features.onboarding.entity.OnboardingScrData
import com.thomas200593.mdm.features.onboarding.entity.OnboardingStatus

@Composable
fun ScrOnboarding(
    vm: VMOnboarding = hiltViewModel()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit, block = { vm.onEvent(VMOnboarding.Ui.Events.OnOpenEvent) })
    ScrOnboarding(
        dataState = uiState.dataState,
        onNavPrevPage = { vm.onEvent(VMOnboarding.Ui.Events.OnNavPrevPage) },
        onNavNextPage = { vm.onEvent(VMOnboarding.Ui.Events.OnNavNextPage) },
        onNavFinish = { vm.onEvent(VMOnboarding.Ui.Events.OnNavFinish) }
    )
}

@Composable
private fun ScrOnboarding(
    dataState: VMOnboarding.Ui.DataState,
    onNavPrevPage: () -> Unit,
    onNavNextPage: () -> Unit,
    onNavFinish: () -> Unit
) = when (dataState) {
    VMOnboarding.Ui.DataState.Loading -> ScrLoading()
    is VMOnboarding.Ui.DataState.Success -> ScreenContent(
        data = dataState.data,
        onNavPrevPage = onNavPrevPage,
        onNavNextPage = onNavNextPage,
        onNavFinish = onNavFinish
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    data: OnboardingScrData,
    onNavPrevPage: () -> Unit,
    onNavNextPage: () -> Unit,
    onNavFinish: () -> Unit
) = Scaffold(
    topBar = {
        TopAppBar(
            title = {},
            actions = {
                BtnConfLang(
                    onClick = {/*TODO*/},
                    languageIcon = data.confCommon.localization.country.flag,
                    languageName = data.confCommon.localization.country.name
                )
            }
        )
    },
    content = {
        Surface(modifier = Modifier.padding(it)) {
            Column(modifier = Modifier.fillMaxSize()) {
                OnboardingImages(
                    modifier = Modifier.fillMaxWidth().weight(1.0f),
                    currentPage = data.list[data.listCurrentIndex]
                )
                OnboardingDetails(
                    modifier = Modifier.weight(1.0f).padding(16.dp),
                    currentPage = data.list[data.listCurrentIndex]
                )
            }
        }
    },
    bottomBar = {
        BottomAppBar(
            content = {
                OnboardingNavigation(
                    currentIndex = data.listCurrentIndex,
                    maxIndex = data.listMaxIndex,
                    onNavPrevPage = onNavPrevPage,
                    onNavNextPage = onNavNextPage,
                    onNavFinish = onNavFinish
                )
            }
        )
    }
)

@Composable
private fun OnboardingImages(
    modifier: Modifier,
    currentPage: Onboarding
) {
    Box(modifier = modifier) {
        SubcomposeAsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest
                .Builder(LocalContext.current).crossfade(250).data(currentPage.imageRes)
                .build(),
            contentDescription = null,
            loading = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) { CircularProgressIndicator() }
            },
            contentScale = ContentScale.FillWidth
        )
        Box(modifier = Modifier.fillMaxSize().align(Alignment.BottomCenter)
            .graphicsLayer { alpha = 0.6f }.background(
                verticalGradient(colorStops = arrayOf(
                    Pair(0.6f, Color.Transparent),
                    Pair(1.0f, MaterialTheme.colorScheme.onSurface)
                ))
            )
        )
    }
}

@Composable
private fun OnboardingDetails(
    modifier: Modifier,
    currentPage: Onboarding
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { TxtLgTitle(text = stringResource(currentPage.title)) }
        item { TxtMdBody(text = stringResource(currentPage.description)) }
    }
}

@Composable
fun OnboardingNavigation(
    currentIndex: Int,
    maxIndex: Int,
    onNavPrevPage: () -> Unit,
    onNavNextPage: () -> Unit,
    onNavFinish: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(0.5f),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val btnPrevState by remember(currentIndex, maxIndex) {
                derivedStateOf { if (currentIndex > 0) Pair(true, onNavPrevPage) else Pair(false){} }
            }
            AnimatedVisibility(
                visible = btnPrevState.first,
                content = { BtnPrevious(onClick = btnPrevState.second, label = "Previous") }
            )
        }
        Row(
            modifier = Modifier.weight(0.5f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val btnNextColor = Pair(MaterialTheme.colorScheme.tertiaryContainer, MaterialTheme.colorScheme.onTertiaryContainer)
            val btnNextState by remember(currentIndex, maxIndex) {
                derivedStateOf {
                    if (currentIndex < maxIndex) Pair(Triple("Next", Icons.AutoMirrored.Default.NavigateNext, null), onNavNextPage)
                    else Pair(Triple("Finish", Icons.Default.Check, BorderStroke(1.dp, btnNextColor.second)), onNavFinish)
                }
            }

            BtnNext(
                onClick = btnNextState.second,
                label = btnNextState.first.first,
                icon = btnNextState.first.second,
                border = btnNextState.first.third,
                colors =
                    if (currentIndex < maxIndex) ButtonDefaults.textButtonColors()
                    else ButtonDefaults.textButtonColors().copy(
                        containerColor = btnNextColor.first,
                        contentColor = btnNextColor.second
                    )
            )
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = true
)
private fun PreviewScrOnboarding() = Theme.AppTheme(
    darkThemeEnabled = true,
    dynamicColorEnabled = false,
    contrastAccent = ContrastAccent.defaultValue,
    fontSize = FontSize.defaultValue,
    content = {
        ScrOnboarding(
            onNavPrevPage = {},
            onNavNextPage = {},
            onNavFinish = {},
            dataState = VMOnboarding.Ui.DataState.Success(
                data = OnboardingScrData(
                    confCommon = Common(
                        ui = UI(
                            theme = com.thomas200593.mdm.features.conf.__theme.entity.Theme.defaultValue,
                            contrastAccent = ContrastAccent.defaultValue,
                            fontSize = FontSize.defaultValue,
                            dynamicColor = DynamicColor.defaultValue
                        ),
                        localization = Localization(
                            country = Country.defaultValue,
                            language = Language.defaultValue
                        ),
                        firstTimeStatus = FirstTimeStatus.YES,
                        onboardingStatus = OnboardingStatus.SHOW,
                    ),
                    list = listOf(
                        Onboarding(
                            imageRes = R.drawable.onboard_image_1,
                            title = R.string.onboarding_title_1,
                            description = R.string.onboarding_desc_1
                        ),
                        Onboarding(
                            imageRes = R.drawable.onboard_image_2,
                            title =  R.string.onboarding_title_2,
                            description = R.string.onboarding_desc_2
                        ),
                        Onboarding(
                            imageRes = R.drawable.onboard_image_3,
                            title =  R.string.onboarding_title_3,
                            description = R.string.onboarding_desc_3
                        )
                    ),
                    listCurrentIndex = 2,
                    listMaxIndex = 2
                )
            )
        )
    }
)