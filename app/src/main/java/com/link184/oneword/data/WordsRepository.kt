package com.link184.oneword.data

import android.content.res.Resources
import com.link184.oneword.R
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.io.BufferedReader

interface WordsRepository {
    fun loadWords(): List<Word>

    fun nextWord(): Word
}

class DefaultWordsRepository(
    private val resources: Resources,
    private val wordsDataSource: WordsDataSource,
): WordsRepository {
    override fun loadWords(): List<Word> {
        maybeCacheWords()

        return wordsDataSource.getAllWords()
    }

    private fun maybeCacheWords() {
        val rawDeEsDictionary = resources.openRawResource(R.raw.de_en).bufferedReader().use(BufferedReader::readText)
        val jsonObject = Json.decodeFromString<JsonObject>(rawDeEsDictionary)

        val allWords = jsonObject.map {
            Word(it.value.jsonPrimitive.toString(), it.key)
        }

        wordsDataSource.setAllWords(allWords)
    }

    override fun nextWord(): Word {
        TODO("Not yet implemented")
    }
}