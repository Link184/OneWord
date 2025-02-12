package com.link184.oneword.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class WordNotificationBootReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            WordNotificationWorker.enqueue(context)
        }
    }
}