package com.thomas200593.mdm.core.design_system.util

import java.time.LocalDate
import java.time.ZoneId

fun LocalDate.toEpochMilli(): Long =
    this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()