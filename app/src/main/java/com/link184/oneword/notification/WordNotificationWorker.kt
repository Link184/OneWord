package com.link184.oneword.notification

import android.annotation.SuppressLint
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.link184.oneword.domain.GetNotificationEnabledUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import java.util.Calendar
import java.util.concurrent.TimeUnit

@HiltWorker
class WordNotificationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val wordNotificationFactory: WordNotificationFactory,
    private val getNotificationEnabledUseCase: GetNotificationEnabledUseCase,
    private val nowCalendar: Calendar
) : Worker(context, workerParameters) {
    @SuppressLint("MissingPermission")
    override fun doWork(): Result {
        val notificationsEnabled = getNotificationEnabledUseCase()
        return if (notificationsEnabled) {
            wordNotificationFactory.show()
            enqueueDelayed(context, nowCalendar)
            Result.success()
        } else {
            Result.failure()
        }
    }

    companion object {
        private const val WORK_NAME = "WordNotificationWorker"
        private val workerRequest: PeriodicWorkRequest.Builder =
            PeriodicWorkRequestBuilder<WordNotificationWorker>(1, TimeUnit.DAYS)

        private fun enqueueDelayed(context: Context, nowCalendar: Calendar) {
            val workManager = WorkManager.getInstance(context)

            val startTimeTimestamp = nowCalendar.let {
                it.add(Calendar.DAY_OF_YEAR, 1)
                it.set(Calendar.HOUR, 0)
                it.set(Calendar.MINUTE, 0)
                it.set(Calendar.SECOND, 1)
                it.timeInMillis
            }

            workManager.enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
                workerRequest
                    .setInitialDelay(startTimeTimestamp, TimeUnit.MILLISECONDS)
                    .build()
            )
        }

        fun enqueue(context: Context) {
            val workManager = WorkManager.getInstance(context)
            workManager.enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
                workerRequest.build()
            )
        }

        fun stop(context: Context) {
            val workManager = WorkManager.getInstance(context)
            workManager.cancelUniqueWork(WORK_NAME)

            EntryPointAccessors.fromApplication<WordNotificationFactoryEntryPoint>(context)
                .injectWordNotificationFactory()
                .cancel()
        }
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface WordNotificationFactoryEntryPoint {
    fun injectWordNotificationFactory(): WordNotificationFactory
}