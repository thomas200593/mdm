package com.thomas200593.mdm.core.design_system.util

import kotlin.reflect.KProperty1

inline fun <T> List<T>.filterSort(
    crossinline filter: (T) -> Boolean = { true }, // Default : No filtering
    vararg properties: Pair<KProperty1<T, Comparable<*>?>, Boolean> // Multiple & dynamic sort properties
) = this.filter(filter).sortedWith(compareByMultiple(*properties)).toList()

fun <T> compareByMultiple(vararg properties: Pair<KProperty1<T, Comparable<*>?>, Boolean>): Comparator<T> =
    Comparator { a, b -> properties.asSequence()
        .map { (prop, ascending) ->
            @Suppress("UNCHECKED_CAST")
            compareValues(prop.get(a) as? Comparable<Any?>, prop.get(b) as? Comparable<Any?>)
                .let { if (ascending) it else -it }
        }.firstOrNull { it != 0 } ?: 0
    }