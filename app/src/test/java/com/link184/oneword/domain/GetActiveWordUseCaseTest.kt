package com.link184.oneword.domain

import com.link184.oneword.data.WordsRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class GetActiveWordUseCaseTest {
    private lateinit var classUnderTest: GetActiveWordUseCase
    @MockK(relaxed = true)
    private lateinit var wordsRepository: WordsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        classUnderTest = GetActiveWordUseCase(wordsRepository)
    }

    @Test
    fun `Given false needToUpdateActiveWord When execute Then activeWord is called on WordsRepository`() {
        // Given
        every { wordsRepository.needToChangeActiveWord() } returns false

        // When
        classUnderTest()

        // Then
        verify(exactly = 1) { wordsRepository.activeWord() }
    }

    @Test
    fun `Given true needToUpdateActiveWord When execute Then nextWord is called on WordsRepository`() {
        // Given
        every { wordsRepository.needToChangeActiveWord() } returns true

        // When
        classUnderTest()

        // Then
        verify(exactly = 1) { wordsRepository.nextWord() }
    }
}