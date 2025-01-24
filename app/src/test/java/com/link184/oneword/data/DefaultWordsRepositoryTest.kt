package com.link184.oneword.data

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DefaultWordsRepositoryTest {
    private lateinit var classUnderTest: DefaultWordsRepository

    @Before
    fun setUp() {
        classUnderTest =
            DefaultWordsRepository(InstrumentationRegistry.getInstrumentation().context.resources)
    }

    @Test
    fun `When loadWords Then return a not empty list of words`() {
        val result = classUnderTest.loadWords()

        assertTrue(result.isNotEmpty())
    }
}