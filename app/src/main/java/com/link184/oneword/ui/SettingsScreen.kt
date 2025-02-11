package com.link184.oneword.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.link184.oneword.notification.WordNotificationWorker
import com.link184.oneword.ui.component.Button95
import com.link184.oneword.ui.component.Divider95
import com.link184.oneword.ui.component.Window95

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Window95(
        modifier = modifier
            .fillMaxHeight(),
        headerTitle = "header title",
        offsetX = remember { mutableFloatStateOf(3f) },
        offsetY = remember { mutableFloatStateOf(3f) },
        action = {

        }
    ) {
        Column {
            Button95(
                modifier = Modifier.padding(bottom = 8.dp),
                onClick = { WordNotificationWorker.enqueue(context) }
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Launch notifications"
                )
            }
            Divider95()
            Button95(
                modifier = Modifier.padding(top = 8.dp),
                onClick = { WordNotificationWorker.stop(context) }
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Stop all notifications"
                )
            }
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}