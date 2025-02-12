package com.link184.oneword.domain

import com.link184.oneword.data.WordsRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class SetNotificationEnabledUseCaseTest {
    private lateinit var classUnderTest: SetNotificationEnabledUseCase

    @MockK(relaxed = true)
    private lateinit var wordsRepository: WordsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        classUnderTest = SetNotificationEnabledUseCase(wordsRepository)
    }

    @Test
    fun `When invoke Then call isNotificationsEnabled on WordsRepository`() {
        // When
        classUnderTest(true)

        // Then
        verify { wordsRepository.setNotificationEnabled(true) }
    }
}