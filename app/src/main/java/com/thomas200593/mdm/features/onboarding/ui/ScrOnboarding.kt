package com.thomas200593.mdm.features.onboarding.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.thomas200593.mdm.core.ui.common.Theme
import com.thomas200593.mdm.core.ui.component.BtnConfLang
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
                        languageIcon = String(Character.toChars(0x1F1EE)) +
                                String(Character.toChars(0x1F1E9))
                    )
                }
            )
        },
        content = {
            Surface(modifier = Modifier.padding(it).fillMaxSize()) {
                OnboardingImages()
                OnboardingDetails()
                OnboardingNavigation()
                OnboardingTabSelector()
            }
        }
    )
}

@Composable
private fun OnboardingImages() {}

@Composable
private fun OnboardingDetails() {}

@Composable
private fun OnboardingNavigation() {}

@Composable
private fun OnboardingTabSelector() {}

@Composable
@Preview
private fun Test() = Theme.AppTheme(
    darkThemeEnabled = true,
    dynamicColorEnabled = true,
    contrastAccent = ContrastAccent.DEFAULT,
    fontSize = FontSize.MEDIUM
) { ScrOnboarding() }