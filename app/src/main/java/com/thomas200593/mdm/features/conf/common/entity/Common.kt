package com.thomas200593.mdm.features.conf.common.entity

import com.thomas200593.mdm.features.conf._localization.entity.Localization
import com.thomas200593.mdm.features.conf._ui.entity.UI

data class Common(
    val ui: UI,
    val localization: Localization
)