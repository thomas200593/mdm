package com.thomas200593.mdm.core.design_system.util

import kotlinx.coroutines.flow.MutableStateFlow

inline fun <T> MutableStateFlow<T>.update(function: (T) -> T) {
    while (true) {
        val prev = value;
        val next = function(prev)
        if (compareAndSet(prev, next)) return
    }
}