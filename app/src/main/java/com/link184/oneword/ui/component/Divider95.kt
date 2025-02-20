package com.link184.oneword.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.link184.oneword.ui.theme.Color95

@Composable
fun Divider95(
    modifier: Modifier = Modifier.fillMaxWidth(),
) {
    Column(modifier = modifier) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color95.gray1)
        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color95.white2)
        )
    }
}