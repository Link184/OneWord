package com.link184.oneword.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AppUpdateReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_MY_PACKAGE_REPLACED) {
            WordNotificationWorker.enqueue(context)
        }
    }
}