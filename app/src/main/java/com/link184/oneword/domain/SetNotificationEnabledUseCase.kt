package com.link184.oneword.domain

import com.link184.oneword.data.WordsRepository

class SetNotificationEnabledUseCase(private val wordsRepository: WordsRepository) {
    operator fun invoke(enabled: Boolean) = wordsRepository.setNotificationEnabled(enabled)
}