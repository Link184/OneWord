package com.link184.oneword.data

import com.link184.oneword.OneWordDatabase

interface WordsDataSource {
    fun setAllWords(words: List<Word>)

    fun getAllWords(): List<Word>
}

class DefaultWordsDataSource(
    private val database: OneWordDatabase
) : WordsDataSource {
    private val wordQueries = database.wordQueries

    override fun setAllWords(words: List<Word>) {
        database.transaction {
            words.forEach { word ->
                wordQueries.insert(word.original, word.translation)
            }
        }
    }

    override fun getAllWords(): List<Word> {
        return wordQueries.selectAll { _, wordOriginal, wordTranslated ->
            Word(wordOriginal, wordTranslated)
        }.executeAsList()
    }
}