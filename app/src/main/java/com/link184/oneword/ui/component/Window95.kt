package com.link184.oneword.ui.component

import android.app.Activity.WINDOW_SERVICE
import android.view.WindowManager
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.link184.oneword.ui.theme.Color95

@Composable
fun Window95(
    modifier: Modifier = Modifier,
    headerContent: @Composable RowScope.() -> Unit,
    content: @Composable () -> Unit
) {
    val activity = LocalActivity.current
    val windowManager = activity?.getSystemService(WINDOW_SERVICE) as WindowManager

    // TODO the border have 2 sets of colors (top + left & bottom + right)
    Column(
        modifier = modifier
            .background(Color95.backgroundGrey)
//            .border95(Elevation.Above)
    ) {
        val borderCompensationPadding = 4.dp
        WindowsHeader(
            modifier = Modifier
                .padding(4.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        with (activity.window.attributes) {
                            x += dragAmount.x.toInt()
                            y += dragAmount.y.toInt()
                            windowManager.updateViewLayout(activity.window.decorView, this)
                        }
                    }
                },
        ) {
            headerContent()
        }
        Box(modifier = Modifier.padding(borderCompensationPadding)) {
            content()
        }
    }
}

@Composable
fun WindowsHeader(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    BoxWithConstraints(modifier = modifier) {
        Row(
            modifier = Modifier.background(
                Brush.linearGradient(
                    colorStops = arrayOf(
                        0f to Color(0, 0, 128),
                        1f to Color(16, 52, 166),
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(constraints.maxWidth.toFloat(), 0f),
                    tileMode = TileMode.Repeated
                )
            )
        ) {
            this.content()
        }
    }
}

/**
 * Short version of Window95 where there is only title, and nothing else
 */
@Composable
fun Window95(
    modifier: Modifier = Modifier,
    headerTitle: String,
    action: (Window95Action) -> Unit,
    content: @Composable () -> Unit
) {
    Window95(
        modifier = modifier,
        headerContent = {
            Text(
                modifier = Modifier
                    .weight(1f) // TODO How to add spacing between this text and the buttons only if children has extra space?
                    .padding(4.dp),
                text = headerTitle,
                color = Color.White,
                style = TextStyle.Default.copy(fontWeight = FontWeight.W700)
            )

            val buttonModifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(2.dp)
                .align(Alignment.CenterVertically)

            MinimizeButton95(
                modifier = buttonModifier
            ) { action(Window95Action.MinimizeClicked) }
            MaximizeButton95(
                modifier = buttonModifier
            ) { action(Window95Action.MaximizeClicked) }
            CloseButton95(
                modifier = buttonModifier
            ) { action(Window95Action.CloseClicked) }
        },
        content = content
    )
}

enum class Window95Action {
    MinimizeClicked,
    MaximizeClicked,
    CloseClicked,
}

@Preview
@Composable
fun Window95Preview() {
    Window95(
        headerTitle = "header title",
        action = {

        }
    ) {

    }
}