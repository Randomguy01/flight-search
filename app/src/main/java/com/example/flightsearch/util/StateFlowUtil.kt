package com.example.flightsearch.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * Extension on [Flow], creates a [StateFlow] with defaults to prevent code duplication. Specifically,
 * defaults to an empty list.
 */
fun <T> Flow<List<T>>.listStateIn(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(5_000),
    initialValue: List<T> = emptyList<T>(),
): StateFlow<List<T>> = this.stateIn(
    scope = scope,
    started = started,
    initialValue = initialValue,
)