package com.link184.oneword.data

import android.content.Context
import android.content.SharedPreferences
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DefaultActiveWordPreferenceTest {
    private lateinit var classUnderTest : DefaultActiveWordPreference
    @MockK(relaxed = true)
    private lateinit var sharedPreferences: SharedPreferences

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        val context: Context = mockk()
        every { context.getSharedPreferences(ACTIVE_WORD_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE) } returns sharedPreferences
        classUnderTest = DefaultActiveWordPreference(context)
    }

    @Test
    fun `Given word id When set and get it Then the result must match`() {
        // Given
        val wordId = 666L
        every { sharedPreferences.getLong(ACTIVE_WORD_ID_PREFERENCE_KEY, -1) } returns wordId

        // When
        classUnderTest.activeWordId = wordId
        val actualResult = classUnderTest.activeWordId

        // Then
        assertEquals(wordId, actualResult)
    }
}