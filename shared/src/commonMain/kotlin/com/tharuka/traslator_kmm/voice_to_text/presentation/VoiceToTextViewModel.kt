package com.tharuka.traslator_kmm.voice_to_text.presentation

import com.tharuka.traslator_kmm.core.domain.util.toCommonStateFlow
import com.tharuka.traslator_kmm.voice_to_text.domain.VoiceToTextParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class VoiceToTextViewModel(
    private val parser: VoiceToTextParser,
    coroutineScope: CoroutineScope? = null
) {

    private val viewModelScope = coroutineScope?:CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(VoiceToTextState())
    val state = _state.combine(parser.state){ state, voiceResult->
        state.copy(
            spokenText = voiceResult.result,
            recordError = if(state.canRecord){voiceResult.error} else{"Can't Record without Permission"},
            displayState = when{
                !state.canRecord || voiceResult.error != null -> DisplayState.ERROR
                voiceResult.result.isNotBlank()&& !voiceResult.isSpeaking -> DisplayState.DISPLAYING_RESULTS
                voiceResult.isSpeaking -> DisplayState.SPEAKING
                else -> DisplayState.WAITING_TO_TALK
            }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), VoiceToTextState()).toCommonStateFlow()

    init {
        viewModelScope.launch {
            while (true){

                if(state.value.displayState== DisplayState.SPEAKING){
                    _state.update {
                        it.copy(
                            powerRatios = it.powerRatios + parser.state.value.powerRatio
                        )
                    }
                }
                delay(50L)
            }
        }
    }

    fun onEvent(viceToTextEvent: VoiceToTextEvent){
        when(viceToTextEvent){
            is VoiceToTextEvent.PermissionResult -> {
                _state.update {
                    it.copy(
                        canRecord = viceToTextEvent.isGranted
                    )
                }
            }
            VoiceToTextEvent.Reset -> {
                parser.reset()
                _state.update { VoiceToTextState() }
            }
            is VoiceToTextEvent.ToggleRecord -> {
                toggleRecord(viceToTextEvent.languageCode)
            }
            else -> Unit
        }
    }

    private fun toggleRecord(languageCode: String){
        _state.update { it.copy(powerRatios = emptyList()) }
        parser.cancel()
        if(state.value.displayState== DisplayState.SPEAKING){
            parser.stopListening()
        }else{
            parser.startListening(languageCode)
        }
    }

}