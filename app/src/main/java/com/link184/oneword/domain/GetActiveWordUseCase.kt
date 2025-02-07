package com.link184.oneword.domain

import com.link184.oneword.data.Word
import com.link184.oneword.data.WordsRepository

class GetActiveWordUseCase(private val wordsRepository: WordsRepository) {
    operator fun invoke(): Word {
        return wordsRepository.activeWord()
    }
}