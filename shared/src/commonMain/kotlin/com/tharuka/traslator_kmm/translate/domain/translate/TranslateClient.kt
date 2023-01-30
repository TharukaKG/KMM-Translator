package com.tharuka.traslator_kmm.translate.domain.translate

interface TranslateClient {
    suspend fun translate(
        fromLanguage:String,
        fromText:String,
        toLanguage:String
    ):String
}