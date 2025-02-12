package com.link184.oneword.data

import android.content.Context
import androidx.core.content.edit
import dagger.Lazy
import java.util.Calendar

interface ActiveWordPreference {
    var activeWordId: Long

    var lastUpdateDateCalendar: Calendar

    var enabled: Boolean
}

internal const val ACTIVE_WORD_SHARED_PREFERENCE_NAME = "active_word_preferences"
internal const val ACTIVE_WORD_ID_PREFERENCE_KEY = "active_word_id"
internal const val ACTIVE_WORD_LAST_UPDATE_PREFERENCE_KEY = "active_word_last_update_id"
internal const val ACTIVE_WORD_ENABLED_PREFERENCE_KEY = "active_word_enabled_id"

internal class DefaultActiveWordPreference(
    context: Context,
    private val nowCalendar: Lazy<Calendar>
) : ActiveWordPreference {
    private val activeWordShaderPreference = context.getSharedPreferences(
        ACTIVE_WORD_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE
    )

    override var activeWordId: Long
        get() = activeWordShaderPreference.getLong(ACTIVE_WORD_ID_PREFERENCE_KEY, -1)
        set(value) = activeWordShaderPreference.edit {
            putLong(
                ACTIVE_WORD_ID_PREFERENCE_KEY,
                value
            )
        }

    private var lastUpdateDateTimestamp: Long
        get() = activeWordShaderPreference.getLong(ACTIVE_WORD_LAST_UPDATE_PREFERENCE_KEY, -1)
        set(value) = activeWordShaderPreference.edit {
            putLong(
                ACTIVE_WORD_LAST_UPDATE_PREFERENCE_KEY,
                value
            )
        }

    override var lastUpdateDateCalendar: Calendar
        get() {
            nowCalendar.get().timeInMillis = lastUpdateDateTimestamp
            return nowCalendar.get()
        }
        set(value) {
            lastUpdateDateTimestamp = value.timeInMillis
        }

    override var enabled: Boolean
        get() = activeWordShaderPreference.getBoolean(ACTIVE_WORD_ENABLED_PREFERENCE_KEY, false)
        set(value) = activeWordShaderPreference.edit { putBoolean(ACTIVE_WORD_ENABLED_PREFERENCE_KEY, value) }
}