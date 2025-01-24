package com.link184.oneword.notification

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class WordNotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val wordNotificationFactory: WordNotificationFactory
) : Worker(context, workerParameters) {
    @SuppressLint("MissingPermission")
    override fun doWork(): Result {
        val notification = wordNotificationFactory.buildNotification(applicationContext)

        NotificationManagerCompat.from(applicationContext).notify(666, notification)
        return Result.success()
    }
}