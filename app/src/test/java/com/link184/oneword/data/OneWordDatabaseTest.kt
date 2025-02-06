package com.link184.oneword.data

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.link184.oneword.OneWordDatabase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class OneWordDatabaseTest {
    private lateinit var driver: JdbcSqliteDriver

    @Before
    fun setUp() {
        driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        OneWordDatabase.Schema.create(driver)
    }

    @After
    fun tearDown() {
        driver.close()
    }

    @Test
    fun `Given test record When insert and select it Then they must match`() {
        val database = OneWordDatabase(driver)
        val testWord = Word(original = "test_original_word", translation = "test_translation_word")

        // When
        database.wordQueries.insert(testWord.original, testWord.translation)
        val actualResult: Word =
            database.wordQueries.selectAll { word_id, word_original, word_translated ->
                Word(word_original, word_translated)
            }.executeAsOne()

        // Then
        assertEquals(testWord, actualResult)
    }

    @Test
    fun `Given more test records When insert and selectAll Then they must match`() {
        val database = OneWordDatabase(driver)
        val testWords = listOf(
            Word(original = "test_original_word", translation = "test_translation_word"),
            Word(original = "test_original_word2", translation = "test_translation_word2"),
            Word(original = "test_original_word3", translation = "test_translation_word3")
        )

        // When
        database.transaction {
            testWords.forEach {
                database.wordQueries.insert(it.original, it.translation)
            }
        }
        val actualResult: List<Word> =
            database.wordQueries.selectAll { word_id, word_original, word_translated ->
                Word(word_original, word_translated)
            }.executeAsList()

        // Then
        assertEquals(testWords, actualResult)
    }

    @Test
    fun `When deleteAll Then words table must be empty`() {
        val database = OneWordDatabase(driver)

        // When
        database.wordQueries.deleteAll()
        val actualResult: List<Word> =
            database.wordQueries.selectAll { word_id, word_original, word_translated ->
                Word(word_original, word_translated)
            }.executeAsList()

        // Then
        assertTrue(actualResult.isEmpty())
    }
}