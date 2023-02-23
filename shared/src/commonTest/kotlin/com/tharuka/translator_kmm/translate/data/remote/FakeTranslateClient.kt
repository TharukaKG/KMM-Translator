package com.tharuka.translator_kmm.translate.data.remote

import com.tharuka.traslator_kmm.translate.domain.translate.TranslateClient

class FakeTranslateClient: TranslateClient {

    val translatedText: String = "translated text"

    override suspend fun translate(
        fromLanguage: String,
        fromText: String,
        toLanguage: String
    ): String {
        return translatedText
    }

}