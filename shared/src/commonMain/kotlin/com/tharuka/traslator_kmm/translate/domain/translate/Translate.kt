package com.tharuka.traslator_kmm.translate.domain.translate

import com.tharuka.traslator_kmm.core.domain.language.Language
import com.tharuka.traslator_kmm.core.domain.util.Resource
import com.tharuka.traslator_kmm.translate.domain.history.HistoryDataSource
import com.tharuka.traslator_kmm.translate.domain.history.HistoryItem

class Translate (
    private val client: TranslateClient,
    private val historyDataSource: HistoryDataSource
) {
    suspend fun execute(
        fromText: String,
        fromLanguage: Language,
        toLanguage: Language
    ): Resource<String> {
        try {

            val translatedText = client.translate(
                fromLanguage = fromLanguage.langCode,
                fromText = fromText,
                toLanguage = toLanguage.langCode
            )

            historyDataSource.insertHistoryItem(
                HistoryItem(
                    id = null,
                    fromLanguageCode = fromLanguage.langCode,
                    toLanguageCode = toLanguage.langCode,
                    fromText = fromText,
                    toText = translatedText
                )
            )

            return Resource.Success<String>(translatedText)

        } catch (exception: TranslationException) {
            exception.printStackTrace()
            return Resource.Error<String>(exception)
        }
    }
}