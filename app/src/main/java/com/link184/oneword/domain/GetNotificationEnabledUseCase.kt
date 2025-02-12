package com.link184.oneword.domain

import com.link184.oneword.data.WordsRepository

class GetNotificationEnabledUseCase(private val wordsRepository: WordsRepository) {
    operator fun invoke(): Boolean = wordsRepository.isNotificationsEnabled()
}