package com.link184.oneword.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.link184.oneword.OneWordDatabase
import com.link184.oneword.data.DefaultWordsDataSource
import com.link184.oneword.data.DefaultWordsRepository
import com.link184.oneword.data.WordsDataSource
import com.link184.oneword.data.WordsRepository
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
    fun providesWordsDataSource(database: OneWordDatabase): WordsDataSource {
        return DefaultWordsDataSource(database)
    }


    @Provides
    @Singleton
    fun providesWordsRepository(
        @ApplicationContext context: Context,
        wordsDataSource: WordsDataSource
    ): WordsRepository {
        return DefaultWordsRepository(context.resources, wordsDataSource)
    }

    @Provides
    @Singleton
    fun providesWordNotificationFactory(
        @ApplicationContext context: Context,
        wordsRepository: WordsRepository
    ): WordNotificationFactory {
        return WordNotificationFactory(context, wordsRepository)
    }
}