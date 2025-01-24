package com.link184.oneword.di

import android.content.Context
import com.link184.oneword.data.DefaultWordsRepository
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
object SingletonComponent {
    @Provides
    @Singleton
    fun providesWordsRepository(
        @ApplicationContext context: Context
    ): WordsRepository {
        return DefaultWordsRepository(context.resources)
    }

    @Provides
    @Singleton
    fun providesWordNotificationFactory(wordsRepository: WordsRepository): WordNotificationFactory {
        return WordNotificationFactory(wordsRepository)
    }
}