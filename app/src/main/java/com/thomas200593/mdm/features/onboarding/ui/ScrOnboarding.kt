package com.thomas200593.mdm.features.onboarding.ui

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
import com.thomas200593.mdm.core.ui.component.BtnConfLang
import com.thomas200593.mdm.core.ui.component.BtnNext
import com.thomas200593.mdm.core.ui.component.BtnPrevious
import com.thomas200593.mdm.core.ui.component.CenteredCircularProgressIndicator
import com.thomas200593.mdm.core.ui.component.ScrLoading
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

@Composable
fun ScrOnboarding(
    scrGraph: ScrGraphs.Onboarding,
    vm: VMOnboarding = hiltViewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    stateApp: StateApp = LocalStateApp.current
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit, block = { vm.onScreenEvents(Events.Screen.OnOpen) })
    ScrOnboarding(
        scrGraph = scrGraph,
        componentsState = uiState.componentsState,
        onTopAppBarEvents = vm::onTopAppBarEvents,
        onBottomBarEvents = { vm.onBottomBarEvents(it) },
        onNavFinish = { vm.onBottomBarEvents(it)
            .also { coroutineScope.launch { stateApp.navController.navToInitialization() } } }
    )
}
@Composable
private fun ScrOnboarding(
    scrGraph: ScrGraphs.Onboarding,
    componentsState: ComponentsState,
    onTopAppBarEvents: (Events.TopAppBar) -> Unit,
    onBottomBarEvents: (Events.BottomAppBar) -> Unit,
    onNavFinish: (Events.BottomAppBar) -> Unit
) = when (componentsState) {
    is ComponentsState.Loading -> ScrLoading(loadingLabel = scrGraph.title)
    is ComponentsState.Loaded -> ScreenContent(
        componentsState = componentsState,
        onTopAppBarEvents = onTopAppBarEvents,
        onBottomBarEvents = onBottomBarEvents,
        onNavFinish = onNavFinish
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    componentsState: ComponentsState.Loaded,
    onTopAppBarEvents: (Events.TopAppBar) -> Unit,
    onBottomBarEvents: (Events.BottomAppBar) -> Unit,
    onNavFinish: (Events.BottomAppBar) -> Unit
) = Scaffold(
    topBar = {
        SectionTopBar(
            confCommon = componentsState.confCommon, languages = componentsState.languageList,
            onTopAppBarEvents = onTopAppBarEvents
        )
    },
    content = {
        SectionContent(
            paddingValues = it,
            currentPage = componentsState.onboardingPages[componentsState.listCurrentIndex]
        )
    },
    bottomBar = {
        SectionBottomBar(
            currentIndex = componentsState.listCurrentIndex, maxIndex = componentsState.listMaxIndex,
            onBottomBarEvents = onBottomBarEvents, onNavFinish = onNavFinish
        )
    }
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SectionTopBar(confCommon: Common, languages: List<Language>, onTopAppBarEvents: (Events.TopAppBar) -> Unit) {
    TopAppBar(
        title = {},
        actions = {
            BtnConfLang(
                languages = languages,
                onSelectLanguage = { onTopAppBarEvents(Events.TopAppBar.BtnLanguage.OnSelect(it)) },
                languageIcon = confCommon.localization.language.country.flag
            )
        }
    )
}
@Composable
private fun SectionContent(paddingValues: PaddingValues, currentPage: Onboarding) {
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
private fun PartBanner(modifier: Modifier, currentPage: Onboarding) {
    Box(
        modifier = modifier,
        content = {
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current).crossfade(250).data(currentPage.imageRes).build(),
                contentDescription = null,
                loading = { CenteredCircularProgressIndicator() },
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
private fun PartDetail(modifier: Modifier, currentPage: Onboarding) {
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
    currentIndex: Int, maxIndex: Int,
    onBottomBarEvents: (Events.BottomAppBar) -> Unit, onNavFinish: (Events.BottomAppBar) -> Unit
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
                            val btnPrevState by remember(currentIndex, maxIndex)
                            { derivedStateOf {
                                if (currentIndex > 0) true to { onBottomBarEvents(Events.BottomAppBar.BtnNavActions.PageAction(Events.OnboardingButtonNav.PREV)) }
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
                            val btnNextState by remember(currentIndex, maxIndex) {
                                derivedStateOf {
                                    if (currentIndex < maxIndex) Triple(strNext.first, Icons.AutoMirrored.Default.NavigateNext, null) to { onBottomBarEvents(Events.BottomAppBar.BtnNavActions.PageAction(Events.OnboardingButtonNav.NEXT)) }
                                    else Triple(strNext.second, Icons.Default.Check, BorderStroke(1.dp, btnNextColor.second)) to { onNavFinish(Events.BottomAppBar.BtnNavActions.Finish) }
                                }
                            }
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