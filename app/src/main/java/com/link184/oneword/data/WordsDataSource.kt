package com.link184.oneword.data

import android.content.res.Resources
import com.link184.oneword.OneWordDatabase
import com.link184.oneword.R
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.io.BufferedReader

internal const val NOT_SET_WORD_ID = -1L

interface WordsDataSource {
    fun setAllWords(words: List<Word>)

    fun getAllWords(): List<Word>

    fun getFirstWord(): Word

    fun getWordById(id: Long): Word

    fun bundledWords(): List<Word>
}

class DefaultWordsDataSource(
    private val resources: Resources,
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
        return wordQueries.selectAll(::Word).executeAsList()
    }

    override fun getFirstWord(): Word {
        return wordQueries.selectFirstWord(::Word).executeAsOne()
    }

    override fun getWordById(id: Long): Word {
        return wordQueries.selectById(id, ::Word).executeAsOne()
    }

    override fun bundledWords(): List<Word> {
        val rawDeEsDictionary =
            resources.openRawResource(R.raw.de_en).bufferedReader().use(BufferedReader::readText)
        val jsonObject = Json.decodeFromString<JsonObject>(rawDeEsDictionary)

        return jsonObject.map {
            Word(NOT_SET_WORD_ID, it.value.jsonPrimitive.content, it.key)
        }
    }
}