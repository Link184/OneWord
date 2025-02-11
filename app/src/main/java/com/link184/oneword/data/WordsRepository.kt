package com.link184.oneword.data

import dagger.Lazy
import java.util.Calendar

interface WordsRepository {
    fun nextWord(): Word

    fun activeWord(): Word

    fun needToChangeActiveWord(): Boolean
}

class DefaultWordsRepository(
    private val wordsDataSource: WordsDataSource,
    private val activeWordPreference: ActiveWordPreference,
    private val nowCalendar: Lazy<Calendar>
) : WordsRepository {

    private fun cacheWords() {
        val bundledWordsShuffled = wordsDataSource.bundledWords().shuffled()
        wordsDataSource.setAllWords(bundledWordsShuffled)
        activeWordPreference.activeWordId = wordsDataSource.getFirstWord().id
    }

    override fun nextWord(): Word {
        activeWordPreference.lastUpdateDateCalendar = nowCalendar.get()
        return if (activeWordPreference.activeWordId == NOT_SET_WORD_ID) {
            cacheWords()
            wordsDataSource.getFirstWord()
        } else {
            activeWordPreference.activeWordId++
            wordsDataSource.getWordById(activeWordPreference.activeWordId)
        }
    }

    override fun activeWord(): Word {
        return if (activeWordPreference.activeWordId == NOT_SET_WORD_ID) {
            activeWordPreference.lastUpdateDateCalendar = nowCalendar.get()
            cacheWords()
            wordsDataSource.getFirstWord()
        } else {
            wordsDataSource.getWordById(activeWordPreference.activeWordId)
        }
    }

    override fun needToChangeActiveWord(): Boolean {
        return nowCalendar.get().after(activeWordPreference.lastUpdateDateCalendar)
    }
}