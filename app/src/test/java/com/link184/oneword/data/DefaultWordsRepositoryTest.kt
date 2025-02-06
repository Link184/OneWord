package com.link184.oneword.data

import androidx.test.platform.app.InstrumentationRegistry
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DefaultWordsRepositoryTest {
    private lateinit var classUnderTest: DefaultWordsRepository

    @MockK(relaxed = true)
    private lateinit var wordsDataSource: WordsDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        classUnderTest =
            DefaultWordsRepository(
                InstrumentationRegistry.getInstrumentation().context.resources,
                wordsDataSource
            )
    }

    @Test
    fun `When loadWords Then return a not empty list of words`() {
        val result = classUnderTest.loadWords()

        assertTrue(result.isNotEmpty())
    }
}