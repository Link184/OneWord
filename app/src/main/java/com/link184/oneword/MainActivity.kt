package com.link184.oneword

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.link184.oneword.ui.SettingsScreen
import com.link184.oneword.ui.theme.Compose97Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val cameraPermissionState =
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

        when (cameraPermissionState.status) {
            PermissionStatus.Granted -> {
                SettingsScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }

            is PermissionStatus.Denied -> {
                onCheckNotificationPermission()
                Text(
                    modifier = Modifier.padding(innerPadding),
                    text = "I can't work without notifications permission"
                )
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen {}
}