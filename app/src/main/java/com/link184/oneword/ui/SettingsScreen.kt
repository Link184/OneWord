package com.link184.oneword.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.link184.oneword.notification.WordNotificationWorker

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column {
        Button95(
            modifier = modifier,
            onClick = { WordNotificationWorker.enqueue(context) }
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Launch notifications"
            )
        }

        Button95(
            modifier = modifier,
            onClick = { WordNotificationWorker.stop(context) }
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Stop all notifications"
            )
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}