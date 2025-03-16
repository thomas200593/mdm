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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.thomas200593.mdm.core.ui.component.TxtMdLabel
import com.thomas200593.mdm.features.conf.__language.entity.Language
import com.thomas200593.mdm.features.conf.common.entity.Common
import com.thomas200593.mdm.features.initialization.nav.navToInitialization
import com.thomas200593.mdm.features.onboarding.entity.Onboarding
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
    LaunchedEffect(key1 = Unit, block = { vm.onEvent(VMOnboarding.Events.OnOpenEvent) })
    ScrOnboarding(
        scrGraph = scrGraph,
        scrDataState = uiState.scrDataState,
        onSelectLanguage = { vm.onEvent(VMOnboarding.Events.TopAppBarEvents.BtnLanguageEvents.OnSelect(it)) },
        onNavPrevPage = { vm.onEvent(VMOnboarding.Events.BottomAppBarEvents.BtnNavActions.PageAction(VMOnboarding.Events.OnboardingButtonNav.PREV)) },
        onNavNextPage = { vm.onEvent(VMOnboarding.Events.BottomAppBarEvents.BtnNavActions.PageAction(VMOnboarding.Events.OnboardingButtonNav.NEXT)) },
        onNavFinish = { vm.onEvent(VMOnboarding.Events.BottomAppBarEvents.BtnNavActions.Finish)
            .also { coroutineScope.launch { stateApp.navController.navToInitialization() } } }
    )
}

@Composable
private fun ScrOnboarding(
    scrGraph: ScrGraphs.Onboarding,
    scrDataState: VMOnboarding.ScrDataState,
    onSelectLanguage: (Language) -> Unit,
    onNavPrevPage: () -> Unit,
    onNavNextPage: () -> Unit,
    onNavFinish: () -> Unit
) = when (scrDataState) {
    VMOnboarding.ScrDataState.Loading -> ScrLoading(loadingLabel = scrGraph.title)
    is VMOnboarding.ScrDataState.Loaded -> ScreenContent(
        data = scrDataState.scrData,
        onSelectLanguage = onSelectLanguage,
        onNavPrevPage = onNavPrevPage,
        onNavNextPage = onNavNextPage,
        onNavFinish = onNavFinish
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    data: VMOnboarding.ScrData,
    onSelectLanguage: (Language) -> Unit,
    onNavPrevPage: () -> Unit,
    onNavNextPage: () -> Unit,
    onNavFinish: () -> Unit
) = Scaffold(
    topBar = {
        SectionTopBar(
            confCommon = data.confCommon,
            languages = data.languageList,
            onSelectLanguage = onSelectLanguage
        )
    },
    content = {
        SectionContent(
            paddingValues = it,
            currentPage = data.onboardingPages[data.listCurrentIndex]
        )
    },
    bottomBar = {
        SectionBottomBar(
            currentIndex = data.listCurrentIndex,
            maxIndex = data.listMaxIndex,
            onNavPrevPage = onNavPrevPage,
            onNavNextPage = onNavNextPage,
            onNavFinish = onNavFinish
        )
    }
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SectionTopBar(
    confCommon: Common,
    languages: List<Language>,
    onSelectLanguage: (Language) -> Unit
) {
    TopAppBar(
        title = {},
        actions = {
            var expanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                content = {
                    BtnConfLang(
                        modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true),
                        onClick = { expanded = true },
                        border = null,
                        languageIcon = confCommon.localization.language.country.flag
                    )
                    ExposedDropdownMenu(
                        modifier = Modifier.fillMaxWidth(0.5f),
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        content = {
                            languages.forEach {
                                DropdownMenuItem(
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = { expanded = false; onSelectLanguage(it) },
                                    leadingIcon = { Text(it.country.flag) },
                                    text = { TxtMdLabel(text = it.country.name) },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }
                    )
                }
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
private fun PartBanner(
    modifier: Modifier,
    currentPage: Onboarding
) {
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
private fun PartDetail(
    modifier: Modifier,
    currentPage: Onboarding
) {
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
    currentIndex: Int,
    maxIndex: Int,
    onNavPrevPage: () -> Unit,
    onNavNextPage: () -> Unit,
    onNavFinish: () -> Unit
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
                            { derivedStateOf { if (currentIndex > 0) Pair(true, onNavPrevPage) else Pair(false) {} } }
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
                                    if (currentIndex < maxIndex) Pair(Triple(strNext.first, Icons.AutoMirrored.Default.NavigateNext, null), onNavNextPage)
                                    else Pair(Triple(strNext.second, Icons.Default.Check, BorderStroke(1.dp, btnNextColor.second)), onNavFinish)
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