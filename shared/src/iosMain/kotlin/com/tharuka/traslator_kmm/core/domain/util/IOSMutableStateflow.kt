package com.tharuka.traslator_kmm.core.domain.util

import kotlinx.coroutines.flow.MutableStateFlow

class IOSMutableStateflow<T>(initialValue:T): CommonMutableStateFlow<T>(MutableStateFlow(initialValue))