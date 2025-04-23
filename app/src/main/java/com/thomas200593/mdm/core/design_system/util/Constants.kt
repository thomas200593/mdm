package com.thomas200593.mdm.core.design_system.util

import androidx.compose.ui.unit.dp
import com.thomas200593.mdm.BuildConfig
import java.time.Instant

/*TODO: refactor that to resources*/
object Constants {
    val NOW_EPOCH_SECOND = Instant.now().epochSecond
    val NOW_EPOCH_MILLISECOND = Instant.now().toEpochMilli()
    const val WEEK_IN_SECOND = 604_800
    const val ID = "ID"
    const val STR_EMPTY = ""
    const val STR_SYSTEM = "SYSTEM"
    const val STR_UNDERSCORE = "_"
    const val STR_APP_VERSION = "$STR_UNDERSCORE${BuildConfig.VERSION_NAME}$STR_UNDERSCORE${BuildConfig.BUILD_TYPE}"
    object Dimens {
        val dp8 = 8.dp
        val dp16 = 16.dp
    }
}