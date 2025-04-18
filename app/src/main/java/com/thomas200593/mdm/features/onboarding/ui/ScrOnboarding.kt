package com.thomas200593.mdm.features.onboarding.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.thomas200593.mdm.R
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.core.design_system.state_app.LocalStateApp
import com.thomas200593.mdm.core.design_system.state_app.StateApp
import com.thomas200593.mdm.core.design_system.timber_logger.LocalTimberFileLogger
import com.thomas200593.mdm.core.design_system.timber_logger.TimberFileLogger
import com.thomas200593.mdm.core.ui.component.button.BtnConfLang
import com.thomas200593.mdm.core.ui.component.button.BtnNext
import com.thomas200593.mdm.core.ui.component.button.BtnPrevious
import com.thomas200593.mdm.core.ui.component.screen.InnerCircularProgressIndicator
import com.thomas200593.mdm.core.ui.component.screen.ScrLoading
import com.thomas200593.mdm.core.ui.component.TxtLgTitle
import com.thomas200593.mdm.core.ui.component.TxtMdBody
import com.thomas200593.mdm.features.conf.__language.entity.Language
import com.thomas200593.mdm.features.conf.common.entity.Common
import com.thomas200593.mdm.features.initialization.nav.navToInitialization
import com.thomas200593.mdm.features.onboarding.entity.Onboarding
import com.thomas200593.mdm.features.onboarding.ui.events.Events
import com.thomas200593.mdm.features.onboarding.ui.state.ComponentsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val TAG = "ScrOnboarding"

@Composable
fun ScrOnboarding(
    scrGraph: ScrGraphs.Onboarding, vm: VMOnboarding = hiltViewModel(),
    fileLogger: TimberFileLogger = LocalTimberFileLogger.current, stateApp: StateApp = LocalStateApp.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    fileLogger.log(Log.DEBUG, TAG, "compose:ScrOnboarding -> mounted")
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(
        key1 = Unit,
        block = {
            fileLogger.log(Log.DEBUG, TAG, "event:screen -> ScrOnboarding.Opened")
            vm.onScreenEvent(Events.Screen.Opened)
        }
    )
    ScrOnboarding(
        scrGraph = scrGraph, components = uiState.componentsState,
        onTopBarEvent = {
            fileLogger.log(Log.DEBUG, TAG, "event:topBar -> ${it::class.simpleName}")
            vm.onTopBarEvent(it)
        },
        onBottomBarEvent = {
            fileLogger.log(Log.DEBUG, TAG, "event:bottomBar -> ${it::class.simpleName}")
            vm.onBottomBarEvent(it)
        },
        onOnboardingFinished = {
            fileLogger.log(Log.DEBUG, TAG, "event:onboarding -> finished")
            vm.onBottomBarEvent(it).also {
                coroutineScope.launch {
                    fileLogger.log(Log.DEBUG, TAG, "nav:to -> Initialization")
                    stateApp.navController.navToInitialization()
                }
            }
        }
    )
}
@Composable
private fun ScrOnboarding(
    scrGraph: ScrGraphs.Onboarding, fileLogger: TimberFileLogger = LocalTimberFileLogger.current,
    components: ComponentsState,
    onTopBarEvent: (Events.TopBar) -> Unit, onBottomBarEvent: (Events.BottomBar) -> Unit,
    onOnboardingFinished: (Events.BottomBar) -> Unit
) = when (components) {
    is ComponentsState.Loading -> {
        fileLogger.log(Log.DEBUG, TAG, "ui:componentsState -> Loading(${stringResource(scrGraph.title)})")
        ScrLoading(label = scrGraph.title)
    }
    is ComponentsState.Loaded -> {
        fileLogger.log(Log.DEBUG, TAG, "ui:componentsState -> Loaded")
        ScreenContent(
            components = components,
            onOnboardingFinished = onOnboardingFinished,
            onTopBarEvent = onTopBarEvent,
            onBottomBarEvent = onBottomBarEvent
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    fileLogger: TimberFileLogger = LocalTimberFileLogger.current,
    components: ComponentsState.Loaded, onOnboardingFinished: (Events.BottomBar) -> Unit,
    onTopBarEvent: (Events.TopBar) -> Unit, onBottomBarEvent: (Events.BottomBar) -> Unit
) {
    fileLogger.log(Log.DEBUG, TAG, "compose:ScreenContent -> mounted with ${components.onboardingPages.size} pages")
    Scaffold(
        topBar = { SectionTopBar(confCommon = components.confCommon, languages = components.languages, onTopBarEvent = onTopBarEvent) },
        content = { SectionContent(
            paddingValues = it,
            currentPage = components.onboardingPages[components.currentIndex]
        ) },
        bottomBar = { SectionBottomBar(
            currentIndex = components.currentIndex, maxIndex = components.maxIndex,
            onBottomBarEvents = onBottomBarEvent, onNavFinishEvents = onOnboardingFinished
        ) }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SectionTopBar(
    fileLogger: TimberFileLogger = LocalTimberFileLogger.current, confCommon: Common, languages: List<Language>,
    onTopBarEvent: (Events.TopBar) -> Unit
) {
    fileLogger.log(Log.DEBUG, TAG, "ui:topBar -> initialized with ${languages.size} languages")
    TopAppBar(
        title = {}, actions = {
            BtnConfLang(
                languages = languages,
                onSelectLanguage = {
                    fileLogger.log(Log.DEBUG, TAG, "event:topBar -> BtnLanguage.Selected(${it.code})")
                    onTopBarEvent(Events.TopBar.BtnLanguage.Selected(it))
                },
                languageIcon = confCommon.localization.language.country.flag
            )
        }
    )
}
@Composable
private fun SectionContent(
    fileLogger: TimberFileLogger = LocalTimberFileLogger.current, paddingValues: PaddingValues,
    currentPage: Onboarding
) {
    fileLogger.log(Log.DEBUG, TAG, "ui:section -> rendering page $currentPage")
    Surface(
        modifier = Modifier.padding(paddingValues),
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                content = {
                    PartBanner(modifier = Modifier.fillMaxWidth().weight(1.0f), currentPage = currentPage)
                    PartDetail(modifier = Modifier.weight(1.0f).padding(16.dp), currentPage = currentPage)
                }
            )
        }
    )
}
@Composable
private fun PartBanner(
    fileLogger: TimberFileLogger = LocalTimberFileLogger.current, modifier: Modifier, currentPage: Onboarding
) {
    fileLogger.log(Log.DEBUG, TAG, "ui:banner -> loading image for page $currentPage")
    Box(
        modifier = modifier,
        content = {
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current).crossfade(250).data(currentPage.imageRes).build(),
                contentDescription = null, loading = { InnerCircularProgressIndicator() },
                contentScale = ContentScale.FillWidth
            )
            Box(
                modifier = Modifier.fillMaxSize().align(Alignment.BottomCenter).graphicsLayer { alpha = 0.6f }
                    .background(verticalGradient(colorStops = arrayOf(Pair(0.6f, Color.Transparent), Pair(1.0f, MaterialTheme.colorScheme.onSurface))))
            )
        }
    )
}
@Composable
private fun PartDetail(
    modifier: Modifier, fileLogger: TimberFileLogger = LocalTimberFileLogger.current, currentPage: Onboarding
) {
    fileLogger.log(Log.DEBUG, TAG, "ui:detail -> displaying titleRes=${stringResource(currentPage.title)}, descRes=${stringResource(currentPage.description)}")
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        content = {
            item { TxtLgTitle(text = stringResource(currentPage.title)) }
            item { TxtMdBody(text = stringResource(currentPage.description)) }
        }
    )
}
@Composable
private fun SectionBottomBar(
    fileLogger: TimberFileLogger = LocalTimberFileLogger.current,
    currentIndex: Int, maxIndex: Int,
    onBottomBarEvents: (Events.BottomBar) -> Unit, onNavFinishEvents: (Events.BottomBar) -> Unit
) {
    BottomAppBar (
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Row(
                        modifier = Modifier.weight(0.5f),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            val strPrev = stringResource(R.string.str_back)
                            val btnPrevState by remember(currentIndex, maxIndex) { derivedStateOf {
                                if (currentIndex > 0) true to {
                                    fileLogger.log(Log.DEBUG, TAG, "event:bottomBar -> clicked:PREV ($currentIndex → ${currentIndex - 1})")
                                    onBottomBarEvents(Events.BottomBar.NavButton.Page(Events.Action.PREV))
                                }
                                else false to {}
                            } }
                            AnimatedVisibility(
                                visible = btnPrevState.first,
                                content = { BtnPrevious(onClick = btnPrevState.second, label = strPrev) }
                            )
                        }
                    )
                    Row(
                        modifier = Modifier.weight(0.5f),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            val strNext = Pair(stringResource(R.string.str_next), stringResource(R.string.str_finish))
                            val btnNextColor = Pair(MaterialTheme.colorScheme.tertiaryContainer, MaterialTheme.colorScheme.onTertiaryContainer)
                            val btnNextState by remember(currentIndex, maxIndex) { derivedStateOf {
                                if (currentIndex < maxIndex) Triple(strNext.first, Icons.AutoMirrored.Default.NavigateNext, null) to {
                                    fileLogger.log(Log.DEBUG, TAG, "event:bottomBar -> clicked:NEXT ($currentIndex → ${currentIndex + 1})")
                                    onBottomBarEvents(Events.BottomBar.NavButton.Page(Events.Action.NEXT))
                                }
                                else Triple(strNext.second, Icons.Default.Check, BorderStroke(1.dp, btnNextColor.second)) to {
                                    fileLogger.log(Log.INFO, TAG, "event:bottomBar -> clicked:FINISH at last page ($currentIndex)")
                                    onNavFinishEvents(Events.BottomBar.NavButton.Finish)
                                }
                            } }
                            BtnNext(
                                onClick = btnNextState.second,
                                label = btnNextState.first.first,
                                icon = btnNextState.first.second,
                                border = btnNextState.first.third,
                                colors =
                                    if (currentIndex < maxIndex) ButtonDefaults.textButtonColors()
                                    else ButtonDefaults.textButtonColors().copy(containerColor = btnNextColor.first, contentColor = btnNextColor.second)
                            )
                        }
                    )
                }
            )
        }
    )
}