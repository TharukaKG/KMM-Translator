package com.tharuka.traslator_kmm.translate.data.history

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.tharuka.traslator_kmm.core.domain.util.CommonFlow
import com.tharuka.traslator_kmm.core.domain.util.toCommonFlow
import com.tharuka.traslator_kmm.database.TranslateDatabase
import com.tharuka.traslator_kmm.translate.domain.history.HistoryDataSource
import com.tharuka.traslator_kmm.translate.domain.history.HistoryItem
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class SQLDelightHistoryDataSource(
    db: TranslateDatabase
): HistoryDataSource {

    private val queries = db.translateQueries

    override fun history(): CommonFlow<List<HistoryItem>> {
        return queries.getHistoty().asFlow().mapToList().map { history->
            history.map { entity->
                entity.toHistoryItem()
            }
        }.toCommonFlow()
    }

    override suspend fun insertHistoryItem(item: HistoryItem) {
        queries.insertHistoryEntity(
            id = item.id,
            fromLanguage = item.fromLanguageCode,
            toLanguage = item.toLanguageCode,
            fromText = item.fromText,
            toText = item.toText,
            timeStamp = Clock.System.now().toEpochMilliseconds()
        )
    }
}