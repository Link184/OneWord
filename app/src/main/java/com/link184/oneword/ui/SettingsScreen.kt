package com.link184.oneword.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.link184.oneword.notification.WordNotificationWorker
import com.link184.oneword.ui.component.Button95
import com.link184.oneword.ui.component.Divider95
import com.link184.oneword.ui.component.Window95
import com.link184.oneword.ui.component.Window95Action
import kotlin.system.exitProcess

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Window95(
        modifier = modifier
            .fillMaxHeight(),
        headerTitle = "One Word",
        offsetX = remember { mutableFloatStateOf(30f) },
        offsetY = remember { mutableFloatStateOf(30f) },
        action = {
            when (it) {
                Window95Action.MinimizeClicked -> Unit
                Window95Action.MaximizeClicked -> Unit
                Window95Action.CloseClicked -> exitProcess(0)
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val settingsViewModel: SettingsViewModel = viewModel()
            Button95(
                modifier = Modifier.padding(bottom = 8.dp),
                onClick = {
                    settingsViewModel.onLaunchNotifications()
                    WordNotificationWorker.enqueue(context)
                }
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Launch notifications"
                )
            }
            Divider95()
            Button95(
                modifier = Modifier.padding(top = 8.dp),
                onClick = {
                    settingsViewModel.onDisableNotifications()
                    WordNotificationWorker.stop(context)
                }
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