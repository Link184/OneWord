package com.link184.oneword.domain

import com.link184.oneword.data.WordsRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test


class MoveToNextWordUseCaseTest {
    private lateinit var classUnderTest: MoveToNextWordUseCase
    @MockK(relaxed = true)
    private lateinit var wordsRepository: WordsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        classUnderTest = MoveToNextWordUseCase(wordsRepository)
    }

    @Test
    fun `When execute Then nextWord is called on WordsRepository`() {
        // When
        classUnderTest()

        // Then
        verify(exactly = 1) { wordsRepository.nextWord() }
    }
}