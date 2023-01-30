package com.tharuka.traslator_kmm.translate.data.history

import com.tharuka.traslator_kmm.translate.domain.history.HistoryItem
import database.HistoryEntity

fun HistoryEntity.toHistoryItem(): HistoryItem{
    return HistoryItem(
        id = id,
        fromLanguageCode = fromLanguage,
        toLanguageCode = toLanguage,
        fromText = fromText,
        toText = toText
    )
}