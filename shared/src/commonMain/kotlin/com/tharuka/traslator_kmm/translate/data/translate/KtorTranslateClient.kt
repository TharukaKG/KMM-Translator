package com.tharuka.traslator_kmm.translate.data.translate

import com.tharuka.traslator_kmm.NetworkConstants
import com.tharuka.traslator_kmm.translate.domain.translate.TranslateClient
import com.tharuka.traslator_kmm.translate.domain.translate.TranslateError
import com.tharuka.traslator_kmm.translate.domain.translate.TranslationException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.errors.*

class KtorTranslateClient(
    private val httpClient: HttpClient
): TranslateClient {

    override suspend fun translate(
        fromLanguage: String,
        fromText: String,
        toLanguage: String
    ): String {
        val result = try {
            httpClient.post {
                url(NetworkConstants.BASE_URL+"/translate")
                contentType(ContentType.Application.Json)
                setBody(
                    TranslateDto(
                        textToTranslate = fromText,
                        sourceLanguageCode = fromLanguage,
                        targetLanguageCode = toLanguage
                    )
                )
            }
        }catch (exception:IOException){
            throw TranslationException(TranslateError.SERVICE_UNAVAILABLE)
        }

        when(result.status.value){
            in 200..299 ->Unit
            in 400..499 -> throw TranslationException(TranslateError.CLIENT_ERROR)
            500 -> throw TranslationException(TranslateError.SERVER_ERROR)
            else -> throw TranslationException(TranslateError.UNKNOWN_ERROR)
        }

        return try {
            result.body<TranslatedDto>().translatedText
        }catch (exception:Exception){
            throw TranslationException(TranslateError.SERVER_ERROR)
        }

    }

}