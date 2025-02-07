package com.link184.oneword.data

interface WordsRepository {
    fun loadWords(): List<Word>

    fun nextWord(): Word
}

class DefaultWordsRepository(
    private val wordsDataSource: WordsDataSource,
    private val activeWordPreference: ActiveWordPreference,
) : WordsRepository {
    override fun loadWords(): List<Word> {
        if (activeWordPreference.activeWordId == NOT_SET_WORD_ID) {
            cacheWords()
        }

        return wordsDataSource.getAllWords()
    }

    private fun cacheWords() {
        val bundledWordsShuffled = wordsDataSource.bundledWords().shuffled()
        wordsDataSource.setAllWords(bundledWordsShuffled)
        activeWordPreference.activeWordId = wordsDataSource.getFirstItem().id
    }

    override fun nextWord(): Word {
        TODO("Not yet implemented")
    }
}