package com.link184.oneword.notification

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class WordNotificationWorker(
    context: Context,
    workerParameters: WorkerParameters
): Worker(context, workerParameters) {
    override fun doWork(): Result {
        return Result.success()
    }
}