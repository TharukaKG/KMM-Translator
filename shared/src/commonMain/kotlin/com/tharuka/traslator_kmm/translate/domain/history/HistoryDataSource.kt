package com.tharuka.traslator_kmm.translate.domain.history

import com.tharuka.traslator_kmm.core.domain.util.CommonFlow

interface HistoryDataSource {
    fun history():CommonFlow<List<HistoryItem>>
    suspend fun insertHistoryItem(item:HistoryItem)
}