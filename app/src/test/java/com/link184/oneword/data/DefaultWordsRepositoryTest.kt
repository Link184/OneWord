package com.link184.oneword.data

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DefaultWordsRepositoryTest {
    private lateinit var classUnderTest: DefaultWordsRepository

    @MockK(relaxed = true)
    private lateinit var wordsDataSource: WordsDataSource

    @MockK(relaxed = true)
    private lateinit var activeWordPreference: ActiveWordPreference

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        classUnderTest =
            DefaultWordsRepository(
                wordsDataSource,
                activeWordPreference
            )
    }

    @Test
    fun `Given active word not set When loadWords Then cache words and set the first active word`() {
        // Given
        every { activeWordPreference.activeWordId } returns -1
        val bundledWords = listOf(Word(22, "originalWord", "translatedWord"))
        every { wordsDataSource.bundledWords() } returns bundledWords

        // When
        classUnderTest.loadWords()

        // Then
        verify(exactly = 1) { wordsDataSource.setAllWords(bundledWords) }
        verify(exactly = 1) { activeWordPreference.activeWordId = any() }
        verify(exactly = 1) { wordsDataSource.getAllWords() }
    }
}