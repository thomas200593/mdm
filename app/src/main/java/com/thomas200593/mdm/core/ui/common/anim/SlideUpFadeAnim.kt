package com.thomas200593.mdm.core.ui.common.anim

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

private val slideFadeIn: EnterTransition = fadeIn() + slideInVertically { fullHeight -> fullHeight }
private val slideFadeOut: ExitTransition = fadeOut() + slideOutVertically { fullHeight -> fullHeight }
@Composable fun SlideUpFadeAnim(
    modifier : Modifier = Modifier,
    visible : Boolean,
    label : String = "AnimatedVisibility",
    content : @Composable AnimatedVisibilityScope.() -> Unit
) = AnimatedVisibility(
    modifier = modifier,
    visible = visible,
    enter = slideFadeIn,
    exit = slideFadeOut,
    label = label,
    content = content
)