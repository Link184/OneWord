package com.link184.oneword.ui

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.link184.oneword.notification.WordNotificationWorker
import java.util.concurrent.TimeUnit

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val workerRequest: WorkRequest = remember {
        PeriodicWorkRequestBuilder<WordNotificationWorker>(1, TimeUnit.DAYS)
            .build()
    }
    val context = LocalContext.current

    val workManager = remember {
        WorkManager
            .getInstance(context)
    }

    Button(
        modifier = modifier,
        onClick = {
            workManager.enqueue(workerRequest)
        }
    ) {
        Text("Launch notifications")
    }
}