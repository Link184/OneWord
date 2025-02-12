package com.link184.oneword.ui

import androidx.lifecycle.ViewModel
import com.link184.oneword.domain.SetNotificationEnabledUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val setNotificationEnabledUseCase: SetNotificationEnabledUseCase
) : ViewModel() {
    fun onDisableNotifications() {
        setNotificationEnabledUseCase(false)
    }

    fun onLaunchNotifications() {
        setNotificationEnabledUseCase(true)
    }
}