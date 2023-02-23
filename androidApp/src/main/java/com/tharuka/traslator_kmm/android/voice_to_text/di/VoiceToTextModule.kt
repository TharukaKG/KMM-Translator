package com.tharuka.traslator_kmm.android.voice_to_text.di

import android.app.Application
import com.tharuka.traslator_kmm.android.voice_to_text.data.AndroidVoiceToTextParser
import com.tharuka.traslator_kmm.voice_to_text.domain.VoiceToTextParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object VoiceToTextModule {
    @Provides
    fun provideVoiceToTextParser(app: Application): VoiceToTextParser{
        return AndroidVoiceToTextParser(app)
    }
}
