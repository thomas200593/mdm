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
    visible : Boolean, content : @Composable AnimatedVisibilityScope.() -> Unit, modifier: Modifier = Modifier
) = AnimatedVisibility(visible = visible, modifier = modifier, enter = slideFadeIn, exit = slideFadeOut, content = content)