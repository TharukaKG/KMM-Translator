package com.tharuka.traslator_kmm.android.di

import android.app.Application
import com.squareup.sqldelight.db.SqlDriver
import com.tharuka.traslator_kmm.database.TranslateDatabase
import com.tharuka.traslator_kmm.translate.data.history.SQLDelightHistoryDataSource
import com.tharuka.traslator_kmm.translate.data.local.DatabaseDriverFactory
import com.tharuka.traslator_kmm.translate.data.remote.HttpClientFactory
import com.tharuka.traslator_kmm.translate.data.translate.KtorTranslateClient
import com.tharuka.traslator_kmm.translate.domain.history.HistoryDataSource
import com.tharuka.traslator_kmm.translate.domain.translate.Translate
import com.tharuka.traslator_kmm.translate.domain.translate.TranslateClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesHttpClient(): HttpClient {
        return HttpClientFactory().create()
    }

    @Provides
    @Singleton
    fun providesTranslateClient(
        httpClient: HttpClient
    ): TranslateClient {
        return KtorTranslateClient(httpClient)
    }

    @Provides
    @Singleton
    fun providesSQLDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).create()
    }

    @Provides
    @Singleton
    fun providesHistoryDataSource(
        sqlDriver: SqlDriver
    ): HistoryDataSource {
        return SQLDelightHistoryDataSource(TranslateDatabase(sqlDriver))
    }

    @Provides
    @Singleton
    fun providesTranslateUseCase(
        client: TranslateClient,
        dataSource: HistoryDataSource
    ): Translate{
        return Translate(client, dataSource)
    }

}