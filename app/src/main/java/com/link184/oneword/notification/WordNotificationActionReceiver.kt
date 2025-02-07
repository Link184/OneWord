package com.link184.oneword.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.link184.oneword.domain.MoveToNextWordUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WordNotificationActionReceiver : BroadcastReceiver() {
    @Inject
    internal lateinit var wordNotificationFactory: WordNotificationFactory
    @Inject
    internal lateinit var moveToNextWordUseCase: MoveToNextWordUseCase

    override fun onReceive(context: Context, intent: Intent?) {
        when (intent?.action) {
            DISMISS_INTENT_ACTION -> WordNotificationWorker.enqueue(context)
            NEXT_WORD_INTENT_ACTION -> {
                moveToNextWordUseCase()
                WordNotificationWorker.enqueue(context)
            }
            CANCELED_INTENT_ACTION -> wordNotificationFactory.cancel()
        }
    }

    companion object {
        const val DISMISS_INTENT_ACTION = "com.link184.oneword.WORD_NOTIFICATION_DISMISSED"
        const val CANCELED_INTENT_ACTION = "com.link184.oneword.WORD_NOTIFICATION_CANCELED"
        const val NEXT_WORD_INTENT_ACTION = "com.link184.oneword.WORD_NOTIFICATION_NEXT"
    }
}
