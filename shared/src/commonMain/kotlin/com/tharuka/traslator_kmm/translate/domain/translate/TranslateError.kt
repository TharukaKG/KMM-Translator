package com.tharuka.traslator_kmm.translate.domain.translate

enum class TranslateError {
    CLIENT_ERROR,
    SERVICE_UNAVAILABLE,
    SERVER_ERROR,
    UNKNOWN_ERROR
}

class TranslationException(val error: TranslateError): Exception("some error occur while translating ${error.name}")