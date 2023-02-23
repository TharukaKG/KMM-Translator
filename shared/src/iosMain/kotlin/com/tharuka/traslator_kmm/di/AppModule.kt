package com.tharuka.traslator_kmm.di

import com.tharuka.traslator_kmm.database.TranslateDatabase
import com.tharuka.traslator_kmm.translate.data.history.SQLDelightHistoryDataSource
import com.tharuka.traslator_kmm.translate.data.local.DatabaseDriverFactory
import com.tharuka.traslator_kmm.translate.data.remote.HttpClientFactory
import com.tharuka.traslator_kmm.translate.data.translate.KtorTranslateClient
import com.tharuka.traslator_kmm.translate.domain.history.HistoryDataSource
import com.tharuka.traslator_kmm.translate.domain.translate.Translate
import com.tharuka.traslator_kmm.translate.domain.translate.TranslateClient

class AppModule {

    val historyDataSource: HistoryDataSource by lazy {
        SQLDelightHistoryDataSource(
            TranslateDatabase(
                DatabaseDriverFactory().create()
            )
        )
    }

    private val translateClient: TranslateClient by lazy {
        KtorTranslateClient(
            HttpClientFactory().create()
        )
    }

    val translateUseCase: Translate by lazy {
        Translate(translateClient, historyDataSource)
    }

}