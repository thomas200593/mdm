package com.thomas200593.mdm.core.design_system.timber_logger

import androidx.compose.runtime.staticCompositionLocalOf

val LocalTimberFileLogger = staticCompositionLocalOf<TimberFileLogger>
{ error(message = "${TimberFileLogger::class.simpleName} Not Provided!") }