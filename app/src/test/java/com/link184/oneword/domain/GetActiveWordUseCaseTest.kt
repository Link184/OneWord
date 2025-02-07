package com.link184.oneword.domain

import com.link184.oneword.data.WordsRepository
import io.mockk.MockKAnnotations
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
    fun `When execute Then activeWord is called on WordsRepository`() {
        // When
        classUnderTest()

        // Then
        verify(exactly = 1) { wordsRepository.activeWord() }
    }
}