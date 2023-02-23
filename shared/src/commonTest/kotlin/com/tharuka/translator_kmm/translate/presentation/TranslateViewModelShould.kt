package com.tharuka.translator_kmm.translate.presentation

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.tharuka.translator_kmm.translate.data.local.FakeHistoryDataSource
import com.tharuka.translator_kmm.translate.data.remote.FakeTranslateClient
import com.tharuka.traslator_kmm.core.presentation.UiLanguage
import com.tharuka.traslator_kmm.translate.domain.history.HistoryItem
import com.tharuka.traslator_kmm.translate.domain.translate.Translate
import com.tharuka.traslator_kmm.translate.presentation.TranslateEvent
import com.tharuka.traslator_kmm.translate.presentation.TranslateState
import com.tharuka.traslator_kmm.translate.presentation.TranslateViewModel
import com.tharuka.traslator_kmm.translate.presentation.UiHistoryItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test


class TranslateViewModelShould {

    private lateinit var viewModel: TranslateViewModel
    private lateinit var translateClient: FakeTranslateClient
    private lateinit var historyDataSource: FakeHistoryDataSource
    private lateinit var translate: Translate

    @BeforeTest
    fun setup(){
        translateClient = FakeTranslateClient()
        historyDataSource = FakeHistoryDataSource()
        translate = Translate(translateClient, historyDataSource)
        viewModel = TranslateViewModel(translate, historyDataSource, CoroutineScope(Dispatchers.Default))
    }

    @Test
    fun `emit new TranslateStates when historyData or state changes`() = runBlocking {
        viewModel.state.test {
            val initialState = awaitItem()
            assertThat(initialState).isEqualTo(TranslateState())

            val item  = HistoryItem(
                id = 0,
                fromLanguageCode = "en",
                fromText = "from",
                toLanguageCode = "de",
                toText = "to"
            )
            historyDataSource.insertHistoryItem(item)

            val state = awaitItem()

            val expected = UiHistoryItem(
                id = item.id!!,
                fromText = item.fromText,
                fromLanguage = UiLanguage.byCode(item.fromLanguageCode),
                toLanguage = UiLanguage.byCode(item.toLanguageCode),
                toText = item.toText
            )
            assertThat(state.history.first()).isEqualTo(expected)
        }
    }

    @Test
    fun `Translate success - state properly updated`() = runBlocking {
        viewModel.state.test {
            val initialValue = awaitItem()
            assertThat(initialValue).isEqualTo(TranslateState())

            viewModel.onTranslateEvent(TranslateEvent.ChangeTranslationText("translate this"))
            awaitItem()

            viewModel.onTranslateEvent(TranslateEvent.Translate)
            val loadingState = awaitItem()

            assertThat(loadingState.isTranslating).isTrue()

            val translatedTextInState = awaitItem().toText

            assertThat(translateClient.translatedText).isEqualTo(translatedTextInState)

        }
    }

}