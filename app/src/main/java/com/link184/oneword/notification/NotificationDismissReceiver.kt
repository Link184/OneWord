package com.link184.oneword.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationDismissReceiver : BroadcastReceiver() {
    @Inject
    internal lateinit var wordNotificationFactory: WordNotificationFactory

    override fun onReceive(context: Context, intent: Intent?) {
        when (intent?.action) {
            DISMISSED_INTENT_ACTION -> WordNotificationWorker.enqueue(context)
            CANCELED_INTENT_ACTION -> wordNotificationFactory.cancel()
            NEXT_WORD_INTENT_ACTION -> WordNotificationWorker.enqueue(context)
        }
    }

    companion object {
        const val DISMISSED_INTENT_ACTION = "com.link184.oneword.WORD_NOTIFICATION_DISMISSED"
        const val CANCELED_INTENT_ACTION = "com.link184.oneword.WORD_NOTIFICATION_CANCELED"
        const val NEXT_WORD_INTENT_ACTION = "com.link184.oneword.WORD_NOTIFICATION_NEXT"
    }
}
