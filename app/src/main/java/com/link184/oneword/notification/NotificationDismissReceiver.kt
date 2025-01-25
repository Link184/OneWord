package com.link184.oneword.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationDismissReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == INTENT_ACTION) {
            WordNotificationWorker.enqueue(context)
        }
    }

    companion object {
        const val INTENT_ACTION = "com.link184.oneword.WORD_NOTIFICATION_DISMISSED"
    }
}
