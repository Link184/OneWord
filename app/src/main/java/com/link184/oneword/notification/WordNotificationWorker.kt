package com.link184.oneword.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.link184.oneword.R

class WordNotificationWorker(
    context: Context,
    workerParameters: WorkerParameters
): Worker(context, workerParameters) {
    override fun doWork(): Result {
        return Result.success()
    }

    private val CHANNEL_ID = "OneWordMainNotification"
    private fun buildNotification() {
        createNotificationChannel()
        var builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("OneWord title")
            .setContentText("OneWord text")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSilent(true)

        NotificationManagerCompat.from(applicationContext).notify(666, builder.build())

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "One Word main notification channel"
            val descriptionText = "All the One Word notification are flowing through this channel"
            val channel = NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_LOW).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}