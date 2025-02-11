package com.link184.oneword.ui

import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.unit.dp
import com.link184.oneword.ui.theme.Color95
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun Button95(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    body: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .background(Color95.backgroundGrey)
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = ButtonIndication95
            ),
        contentAlignment = Alignment.Center
    ) {
        body()
    }
}


object ButtonIndication95 : IndicationNodeFactory {
    private class ButtonIndication95Instance(
        private val interactionSource: InteractionSource,
    ) : Modifier.Node(), DrawModifierNode {
        private val isPressed: MutableState<Boolean> = mutableStateOf(false)

        override fun onAttach() {
            coroutineScope.launch {
                interactionSource.interactions.collectLatest { interaction ->
                    isPressed.value = interaction is PressInteraction.Press
                }
            }
        }

        override fun ContentDrawScope.draw() {
            drawContent()
            val (topLeft, bottomRight) = if (isPressed.value) {
                SolidColor(Color95.gray2) to SolidColor(Color95.white2)
            } else {
                SolidColor(Color95.white2) to SolidColor(Color95.gray2)
            }

            // draw top
            drawLine(
                brush = topLeft,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = 2.dp.toPx()
            )
            // draw left
            drawLine(
                topLeft,
                Offset(0f, 0f),
                Offset(0f, size.height),
                2.dp.toPx()
            )

            // draw right
            drawLine(
                bottomRight,
                Offset(size.width, 0f),
                Offset(size.width, size.height),
                2.dp.toPx()
            )
            // draw bottom
            drawLine(
                brush = bottomRight,
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height),
                strokeWidth = 2.dp.toPx(),
            )
        }

    }

    override fun create(interactionSource: InteractionSource): DelegatableNode {
        return ButtonIndication95Instance(interactionSource)
    }

    override fun equals(other: Any?): Boolean = other === this

    override fun hashCode(): Int = -1
}


// TODO what does it look like?
@Composable
fun CloseButton95(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button95(modifier = modifier, onClick = onClick) {
        Text("X")
    }
}

@Composable
fun MaximizeButton95(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button95(modifier = modifier, onClick = onClick) {
        Text("Max")
    }
}

@Composable
fun MinimizeButton95(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button95(modifier = modifier, onClick = onClick) {
        Text(" _ ")
    }
}