package com.link184.oneword.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.link184.oneword.R
import com.link184.oneword.domain.GetActiveWordUseCase

private const val CHANNEL_ID = "OneWordMainNotification"
private const val NOTIFICATION_ID = 666

class WordNotificationFactory(
    private val context: Context,
    private val getActiveWordUseCase: GetActiveWordUseCase
) {
    private val notificationManager = NotificationManagerCompat.from(context)

    @SuppressLint("MissingPermission")
    fun show() {
        notificationManager.notify(NOTIFICATION_ID, buildNotification(context))
    }

    fun cancel() {
        notificationManager.cancel(NOTIFICATION_ID)
    }

    private fun buildNotification(context: Context): Notification {
        createNotificationChannel(context)

        val word = getActiveWordUseCase()
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("${word.original} : ${word.translation}")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSilent(true)
            .setOngoing(true)
            .setDeleteIntent(buildNotificationActionPendingIntent(WordNotificationActionReceiver.DISMISS_INTENT_ACTION))
            .addAction(
                android.R.drawable.ic_delete,
                "Don't show today",
                buildNotificationActionPendingIntent(WordNotificationActionReceiver.CANCELED_INTENT_ACTION)
            )
            .addAction(
                android.R.drawable.ic_media_next,
                "Next word",
                buildNotificationActionPendingIntent(WordNotificationActionReceiver.NEXT_WORD_INTENT_ACTION)
            )
            .build()
    }

    private fun buildNotificationActionPendingIntent(action: String): PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            0,
            Intent(context, WordNotificationActionReceiver::class.java)
                .setAction(action),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "One Word main notification channel"
            val descriptionText = "All the One Word notification are flowing through this channel"
            val channel =
                NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_LOW).apply {
                    description = descriptionText
                }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}