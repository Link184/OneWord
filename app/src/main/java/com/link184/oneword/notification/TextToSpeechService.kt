package com.link184.oneword.notification

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.speech.tts.TextToSpeech
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class TextToSpeechService : Service() {
    private var germanTextToSpeech: TextToSpeech? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.getStringExtra(ACTIVE_WORD_KEY)?.let { activeWord ->
            if (germanTextToSpeech == null) {
                germanTextToSpeech = TextToSpeech(this) {
                    if (it == TextToSpeech.SUCCESS) {
                        germanTextToSpeech?.setLanguage(Locale.GERMANY)

                        germanTextToSpeech?.speak(activeWord, TextToSpeech.QUEUE_FLUSH, null, activeWord)
                    }
                }
            } else {
                germanTextToSpeech?.speak(activeWord, TextToSpeech.QUEUE_FLUSH, null, activeWord)
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        const val ACTIVE_WORD_KEY = "active_word"
    }
}