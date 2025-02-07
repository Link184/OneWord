package com.link184.oneword.di

import android.content.Context
import android.content.res.Resources
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.link184.oneword.OneWordDatabase
import com.link184.oneword.data.ActiveWordPreference
import com.link184.oneword.data.DefaultActiveWordPreference
import com.link184.oneword.data.DefaultWordsDataSource
import com.link184.oneword.data.DefaultWordsRepository
import com.link184.oneword.data.WordsDataSource
import com.link184.oneword.data.WordsRepository
import com.link184.oneword.domain.GetActiveWordUseCase
import com.link184.oneword.notification.WordNotificationFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataComponent {
    @Provides
    @Singleton
    fun providesResources(@ApplicationContext context: Context): Resources = context.resources

    @Provides
    @Singleton
    fun providesOneWordDatabase(sqlDriver: SqlDriver): OneWordDatabase {
        return OneWordDatabase(sqlDriver)
    }

    @Provides
    @Singleton
    fun providesSqlDriver(
        @ApplicationContext context: Context
    ): SqlDriver {
        return AndroidSqliteDriver(OneWordDatabase.Schema, context, "OneWordDatabase.db")
    }

    @Provides
    @Singleton
    fun providesWordsDataSource(
        resources: Resources,
        database: OneWordDatabase
    ): WordsDataSource {
        return DefaultWordsDataSource(resources, database)
    }

    @Provides
    @Singleton
    fun providesActiveWordPreference(
        @ApplicationContext context: Context
    ) : ActiveWordPreference = DefaultActiveWordPreference(context)

    @Provides
    @Singleton
    fun providesWordsRepository(
        wordsDataSource: WordsDataSource,
        activeWordPreference: ActiveWordPreference
    ): WordsRepository {
        return DefaultWordsRepository(wordsDataSource, activeWordPreference)
    }

    @Provides
    @Singleton
    fun providesWordNotificationFactory(
        @ApplicationContext context: Context,
        getActiveWordUseCase: GetActiveWordUseCase
    ): WordNotificationFactory {
        return WordNotificationFactory(context, getActiveWordUseCase)
    }
}