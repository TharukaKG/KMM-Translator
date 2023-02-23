package com.tharuka.translator_kmm.translate.data.local

import com.tharuka.traslator_kmm.core.domain.util.CommonFlow
import com.tharuka.traslator_kmm.core.domain.util.toCommonFlow
import com.tharuka.traslator_kmm.translate.domain.history.HistoryDataSource
import com.tharuka.traslator_kmm.translate.domain.history.HistoryItem
import kotlinx.coroutines.flow.MutableStateFlow

class FakeHistoryDataSource: HistoryDataSource {

    private val historyItems = listOf<HistoryItem>()
    private val historyItemsMutableStateFlow = MutableStateFlow(historyItems)

    override fun history(): CommonFlow<List<HistoryItem>> {
        return historyItemsMutableStateFlow.toCommonFlow()
    }

    override suspend fun insertHistoryItem(item: HistoryItem) {
        historyItemsMutableStateFlow.value += item
    }
}