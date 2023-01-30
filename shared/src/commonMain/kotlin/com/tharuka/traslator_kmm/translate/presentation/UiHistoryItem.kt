package com.tharuka.traslator_kmm.translate.presentation

import com.tharuka.traslator_kmm.core.presentation.UiLanguage

data class UiHistoryItem(
    val id:Long,
    val fromLanguage: UiLanguage,
    val toLanguage:UiLanguage,
    val fromText:String,
    val toText:String
)