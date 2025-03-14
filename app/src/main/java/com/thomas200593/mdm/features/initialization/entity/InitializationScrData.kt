package com.thomas200593.mdm.features.initialization.entity

import com.thomas200593.mdm.features.conf.common.entity.Common
import com.thomas200593.mdm.features.initialization.ui.VMInitialization

data class InitializationScrData(
    val confCommon: Common,
    val formData: VMInitialization.Ui.FormData
)