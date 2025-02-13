package com.link184.oneword.ui

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.link184.oneword.R
import com.link184.oneword.notification.WordNotificationWorker
import com.link184.oneword.ui.component.Button95
import com.link184.oneword.ui.component.Divider95
import com.link184.oneword.ui.component.Window95
import com.link184.oneword.ui.component.Window95Action

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    notificationPermissionStatus: PermissionStatus,
    onCheckNotificationPermission: () -> Unit
) {
    val activity = LocalActivity.current

    Window95(
        modifier = modifier
            .fillMaxHeight(),
        headerTitle = "One Word",
        action = {
            when (it) {
                Window95Action.MinimizeClicked -> activity?.moveTaskToBack(true)
                Window95Action.MaximizeClicked -> Unit
                Window95Action.CloseClicked -> activity?.finishAndRemoveTask()
            }
        }
    ) {
        when (notificationPermissionStatus) {
            PermissionStatus.Granted -> {
                Settings()
            }

            is PermissionStatus.Denied -> {
                onCheckNotificationPermission()
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "I can't work without notifications permission"
                )
            }
        }
    }
}

@Composable
private fun Settings() {
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val settingsViewModel: SettingsViewModel = viewModel()

        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_launcher_foreground),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
            contentDescription = null
        )

        Divider95()

        Button95(
            modifier = Modifier.padding(vertical = 8.dp),
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

@OptIn(ExperimentalPermissionsApi::class)
@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(notificationPermissionStatus = PermissionStatus.Granted) {}
}