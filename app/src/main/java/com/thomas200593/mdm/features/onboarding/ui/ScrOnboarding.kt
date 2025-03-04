package com.thomas200593.mdm.features.onboarding.ui

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
import androidx.compose.material.icons.automirrored.filled.NavigateBefore
import androidx.compose.material.icons.automirrored.sharp.NavigateNext
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.thomas200593.mdm.core.ui.common.Theme
import com.thomas200593.mdm.core.ui.component.BtnConfLang
import com.thomas200593.mdm.core.ui.component.TxtLgTitle
import com.thomas200593.mdm.core.ui.component.TxtMdBody
import com.thomas200593.mdm.features.conf.__contrast_accent.entity.ContrastAccent
import com.thomas200593.mdm.features.conf.__font_size.entity.FontSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScrOnboarding() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    BtnConfLang(
                        onClick = {},
                        languageIcon = String(Character.toChars(0x1F1EE)) + String(Character.toChars(0x1F1E9)),
                        languageName = "Indonesia"
                    )
                }
            )
        },
        content = {
            Surface(modifier = Modifier.padding(it)) {
                Column(modifier = Modifier.fillMaxSize()) {
                    OnboardingImages(modifier = Modifier.fillMaxWidth().weight(1.0f))
                    OnboardingDetails(modifier = Modifier.weight(1.0f).padding(16.dp))
                }
            }
        },
        bottomBar = {
            BottomAppBar(
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {},
                            content = {
                                Icon(imageVector = Icons.AutoMirrored.Default.NavigateBefore, contentDescription = null)
                            }
                        )
                        Button(
                            onClick = {},
                            content = {
                                Icon(imageVector = Icons.AutoMirrored.Sharp.NavigateNext, contentDescription = null)
                            }
                        )
                    }
                }
            )
        }
    )
}

@Composable
private fun OnboardingImages(modifier: Modifier) {
    Box(modifier = modifier) {
        SubcomposeAsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = null/*TODO*/,
            contentDescription = null,
            loading = {/*TODO*/},
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
private fun OnboardingDetails(modifier: Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { TxtLgTitle(text = "Title") }
        item { TxtMdBody(text = "Body") }
    }
}

@Composable
@Preview
private fun Test() = Theme.AppTheme(
    darkThemeEnabled = true,
    dynamicColorEnabled = false,
    contrastAccent = ContrastAccent.DEFAULT,
    fontSize = FontSize.MEDIUM
) { ScrOnboarding() }