package com.link184.oneword.di

import com.link184.oneword.data.WordsRepository
import com.link184.oneword.domain.GetActiveWordUseCase
import com.link184.oneword.domain.MoveToNextWordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainComponent {
    @Provides
    @Singleton
    fun providesGetActiveWordUseCase(wordsRepository: WordsRepository): GetActiveWordUseCase =
        GetActiveWordUseCase(wordsRepository)

    @Provides
    @Singleton
    fun providesMoveToNextWordUseCase(wordsRepository: WordsRepository): MoveToNextWordUseCase =
        MoveToNextWordUseCase(wordsRepository)
}