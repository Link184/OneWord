package com.link184.oneword.data

import android.content.Context
import androidx.core.content.edit

interface ActiveWordPreference {
    var activeWordId: Long
}

internal const val ACTIVE_WORD_SHARED_PREFERENCE_NAME = "active_word_preferences"
internal const val ACTIVE_WORD_ID_PREFERENCE_KEY = "active_word_id"

internal class DefaultActiveWordPreference(context: Context): ActiveWordPreference {
    private val activeWordShaderPreference = context.getSharedPreferences(
        ACTIVE_WORD_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)

    override var activeWordId: Long
        get() = activeWordShaderPreference.getLong(ACTIVE_WORD_ID_PREFERENCE_KEY, -1)
        set(value) = activeWordShaderPreference.edit { putLong(ACTIVE_WORD_ID_PREFERENCE_KEY, value) }
}