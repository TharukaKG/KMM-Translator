package com.tharuka.traslator_kmm.core.domain.util

import kotlinx.coroutines.flow.MutableStateFlow

actual class CommonMutableStateFlow<T> actual constructor(private val mutableStateFlow: MutableStateFlow<T>): MutableStateFlow<T> by mutableStateFlow