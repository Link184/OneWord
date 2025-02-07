package com.link184.oneword.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class OneWordDatabaseTest {
    @get:Rule
    val oneWordDatabaseRule = OneWordDatabaseRule()

    @Test
    fun `Given test record When insert and select it Then they must match`() {
        // Given
        val database = oneWordDatabaseRule.database
        val testWord = Word(666, original = "test_original_word", translation = "test_translation_word")

        // When
        database.wordQueries.insert(testWord.original, testWord.translation)
        val actualResult: Word =
            database.wordQueries.selectAll(::Word).executeAsOne()

        // Then
        assertNotEquals(testWord, actualResult)
        assertEquals(testWord.original, actualResult.original)
        assertEquals(testWord.translation, actualResult.translation)
    }

    @Test
    fun `Given more test records When insert and selectAll Then they must match`() {
        // Given
        val database = oneWordDatabaseRule.database
        val testWords = listOf(
            Word(1, original = "test_original_word", translation = "test_translation_word"),
            Word(2, original = "test_original_word2", translation = "test_translation_word2"),
            Word(3, original = "test_original_word3", translation = "test_translation_word3")
        )

        // When
        database.transaction {
            testWords.forEach {
                database.wordQueries.insert(it.original, it.translation)
            }
        }
        val actualResult: List<Word> =
            database.wordQueries.selectAll(::Word).executeAsList()

        // Then
        assertEquals(testWords, actualResult)
    }

    @Test
    fun `When deleteAll Then words table must be empty`() {
        // Given
        val database = oneWordDatabaseRule.database

        // When
        database.wordQueries.deleteAll()
        val actualResult: List<Word> =
            database.wordQueries.selectAll(::Word).executeAsList()

        // Then
        assertTrue(actualResult.isEmpty())
    }

    @Test
    fun `Given word When insert and selectById Then get the right record`() {
        // Given
        val database = oneWordDatabaseRule.database
        val testWord = Word(999, "testOriginalWord", "testTranslatedWord")

        // When
        database.wordQueries.insert(testWord.original, testWord.translation)
        val actualResult = database.wordQueries.selectById(1, ::Word).executeAsOne()

        // Then
        assertNotEquals(testWord, actualResult)
        assertEquals(testWord.original, actualResult.original)
        assertEquals(testWord.translation, actualResult.translation)
    }

    @Test
    fun `Given word When insert and selectFirstWord Then get the right record`() {
        // Given
        val database = oneWordDatabaseRule.database
        val testWord = Word(999, "testOriginalWord", "testTranslatedWord")

        // When
        database.wordQueries.insert(testWord.original, testWord.translation)
        val actualResult = database.wordQueries.selectFirstWord(::Word).executeAsOne()

        // Then
        assertNotEquals(testWord, actualResult)
        assertEquals(testWord.original, actualResult.original)
        assertEquals(testWord.translation, actualResult.translation)
    }
}