package com.link184.oneword.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.link184.oneword.notification.WordNotificationWorker

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column {
        Button(
            modifier = modifier,
            onClick = { WordNotificationWorker.enqueue(context) }
        ) {
            Text("Launch notifications")
        }

        Button(
            modifier = modifier,
            onClick = { WordNotificationWorker.stop(context) }
        ) {
            Text("Stop all notifications")
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}