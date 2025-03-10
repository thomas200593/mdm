package com.thomas200593.mdm.core.design_system.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.getAndUpdate

inline fun <T> MutableStateFlow<T>.update(function: (T) -> T) = getAndUpdate { prev -> function(prev).takeIf { it != prev } ?: prev }