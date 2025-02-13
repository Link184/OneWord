package com.link184.oneword

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.link184.delegates.creatableDestroyable
import com.link184.oneword.ui.SettingsScreen
import com.link184.oneword.ui.theme.Compose97Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mediaPlayer by creatableDestroyable(
        value = MediaPlayer(),
        onCreate = {
            it.setDataSource(resources.openRawResourceFd(R.raw.windows_98_intro))
            it.prepare()
            it.start()
        },
        onDestroy = {
            it.release()
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose97Theme {
                MainScreen(::checkNotificationPermission)
            }
        }
    }

    private fun checkNotificationPermission() {
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS), 33
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun MainScreen(
    onCheckNotificationPermission: () -> Unit
) {
    Scaffold(modifier = Modifier.height(270.dp)) { innerPadding ->
        val notificationPermissionState =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                rememberPermissionState(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            } else {
                object : PermissionState {
                    override val permission: String = Manifest.permission.POST_NOTIFICATIONS
                    override val status: PermissionStatus = PermissionStatus.Granted

                    override fun launchPermissionRequest() = Unit
                }
            }

        SettingsScreen(
            modifier = Modifier.padding(innerPadding),
            notificationPermissionStatus = notificationPermissionState.status,
            onCheckNotificationPermission = onCheckNotificationPermission
        )
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen {}
}