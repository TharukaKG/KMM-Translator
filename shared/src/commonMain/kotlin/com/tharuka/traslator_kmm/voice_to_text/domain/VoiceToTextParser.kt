package com.tharuka.traslator_kmm.voice_to_text.domain

import com.tharuka.traslator_kmm.core.domain.util.CommonStateFlow

interface VoiceToTextParser {
    val state:CommonStateFlow<VoiceToTextParserState>
    fun startListening(languageCode:String)
    fun stopListening()
    fun cancel()
    fun reset()
}