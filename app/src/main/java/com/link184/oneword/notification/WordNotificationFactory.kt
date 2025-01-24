package com.link184.oneword.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.link184.oneword.R
import com.link184.oneword.data.WordsRepository

private val CHANNEL_ID = "OneWordMainNotification"

class WordNotificationFactory(
    private val wordsRepository: WordsRepository
) {
    private val word = wordsRepository.loadWords().random()

    fun buildNotification(context: Context): Notification {
        createNotificationChannel(context)
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("The word of the day")
            .setContentText("${word.original} : ${word.translation}")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSilent(true)
            .build()
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "One Word main notification channel"
            val descriptionText = "All the One Word notification are flowing through this channel"
            val channel = NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_LOW).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}