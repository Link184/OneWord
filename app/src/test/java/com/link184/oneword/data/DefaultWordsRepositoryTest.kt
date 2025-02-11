package com.link184.oneword.data

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.Calendar

@RunWith(JUnit4::class)
class DefaultWordsRepositoryTest {
    private lateinit var classUnderTest: DefaultWordsRepository

    @MockK(relaxed = true)
    private lateinit var wordsDataSource: WordsDataSource

    @MockK(relaxed = true)
    private lateinit var activeWordPreference: ActiveWordPreference

    private val nowCalendar: Calendar = Calendar.getInstance()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        classUnderTest =
            DefaultWordsRepository(
                wordsDataSource,
                activeWordPreference,
                nowCalendar
            )
    }

    @Test
    fun `Given valid active word When nextWord Then make sure the next word is fetched from WordsDataSource and active word id is increased +1`() {
        // Given
        val activeWordId: Long = 3
        every { activeWordPreference.activeWordId } returns activeWordId

        // When
        classUnderTest.nextWord()

        // Then
        verify(exactly = 1) { activeWordPreference.activeWordId = activeWordId + 1 }
        verify(exactly = 1) { wordsDataSource.getWordById(activeWordId) }
    }

    @Test
    fun `Given invalid active word When nextWord Then cache bundled words into WordsDataSource and the first word is returned from WordsDataSource`() {
        // Given
        val bundledWords = listOf(Word(22, "originalWord", "translatedWord"))
        every { wordsDataSource.bundledWords() } returns bundledWords
        every { activeWordPreference.activeWordId } returns NOT_SET_WORD_ID

        // When
        classUnderTest.nextWord()

        // Then
        verify(exactly = 1) { wordsDataSource.setAllWords(bundledWords) }
        verify(exactly = 2) { wordsDataSource.getFirstWord() }
        verify(exactly = 1) { activeWordPreference.activeWordId = any() }
    }



    @Test
    fun `Given valid active word When activeWord Then make sure the next word is fetched from WordsDataSource`() {
        // Given
        val activeWordId: Long = 3
        every { activeWordPreference.activeWordId } returns activeWordId

        // When
        classUnderTest.activeWord()

        // Then
        verify(exactly = 1) { wordsDataSource.getWordById(activeWordId) }
    }

    @Test
    fun `Given invalid active word When activeWord Then cache bundled words into WordsDataSource and the active word is returned from WordsDataSource`() {
        // Given
        val word = Word(22, "originalWord", "translatedWord")
        val bundledWords = listOf(word)
        every { wordsDataSource.bundledWords() } returns bundledWords
        every { activeWordPreference.activeWordId } returns NOT_SET_WORD_ID

        // When
        classUnderTest.activeWord()

        // Then
        verify(exactly = 1) { wordsDataSource.setAllWords(bundledWords) }
        verify(exactly = 2) { wordsDataSource.getFirstWord() }
        verify(exactly = 1) { activeWordPreference.activeWordId = any() }
    }

    @Test
    fun `Given last update of active word before today When needToChangeActiveWord Then return true`() {
        // Given
        every { activeWordPreference.lastUpdateDateCalendar } returns Calendar.getInstance().also { it.add(Calendar.DAY_OF_YEAR, -1) }

        // When
        val actualResult = classUnderTest.needToChangeActiveWord()

        // Then
        assertTrue(actualResult)
    }

    @Test
    fun `Given last update of active word today When needToChangeActiveWord Then return false`() {
        // Given
        every { activeWordPreference.lastUpdateDateCalendar } returns Calendar.getInstance()

        // When
        val actualResult = classUnderTest.needToChangeActiveWord()

        // Then
        assertFalse(actualResult)
    }
}