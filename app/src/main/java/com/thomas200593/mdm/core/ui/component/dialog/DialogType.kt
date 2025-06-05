package com.thomas200593.mdm.core.ui.component.dialog

enum class DialogType {
    CONFIRMATION, ERROR, INFORMATION, SUCCESS, WARNING;
    companion object { val defaultValue = INFORMATION }
}