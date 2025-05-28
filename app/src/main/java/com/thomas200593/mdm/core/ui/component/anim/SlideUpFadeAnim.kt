package com.thomas200593.mdm.core.ui.component.anim

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable

private val slideFadeIn: EnterTransition = fadeIn() + slideInVertically { fullHeight -> fullHeight }
private val slideFadeOut: ExitTransition = fadeOut() + slideOutVertically { fullHeight -> fullHeight }
@Composable fun SlideUpFadeAnim(
    visible : Boolean, content : @Composable AnimatedVisibilityScope.() -> Unit
) = AnimatedVisibility(visible = visible, enter = slideFadeIn, exit = slideFadeOut, content = content)