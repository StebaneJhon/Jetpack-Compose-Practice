package com.example.drawingandanimation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.util.Calendar
import java.util.Date
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.nextUp
import kotlin.math.sin

@Composable
fun Clock(
    modifier: Modifier = Modifier,
    time: () -> Long,
    circleRadius: Float,
    outerCircleThickness: Float,
    ) {

    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    Box(
        modifier = modifier
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val width = size.width
            val height = size.height
            circleCenter = Offset(x = width/2f, y = height/2f)
            val date = Date(time())
            val cal = Calendar.getInstance()
            cal.time = date
            val hours = cal.get(Calendar.HOUR_OF_DAY)
            val minutes = cal.get(Calendar.MINUTE)
            val seconds = cal.get(Calendar.SECOND)

            drawCircle(
                style = Stroke(
                    width = outerCircleThickness
                ),
                brush = Brush.linearGradient(
                    listOf(
                        Color.White.copy(0.45f),
                        Color.DarkGray.copy(0.35f)
                    ),
                ),
                radius = circleRadius + outerCircleThickness/2f,
                center = circleCenter
            )

            drawCircle(
                brush = Brush.radialGradient(
                    listOf(
                        Color.White.copy(0.45f),
                        Color.DarkGray.copy(0.25f)
                    )
                ),
                radius = circleRadius,
                center = circleCenter
            )

            drawCircle(
                color = Color.Gray,
                radius = 15f,
                center = circleCenter
            )

            val littleLineLength = circleRadius*0.1f
            val largeLineLength = circleRadius*0.2f

            for (i in 0 until 60) {
                val angleInDegrees = i*360f/60
                val lineInRad = angleInDegrees * PI /180f + PI/2f
                val lineLength = if (i%5 == 0) largeLineLength else littleLineLength
                val lineThickness = if (i%5 == 0) 4f else 2f

                val start = Offset(
                    x = (circleRadius * cos(lineInRad) + circleCenter.x).toFloat(),
                    y = (circleRadius * sin(lineInRad) + circleCenter.y).toFloat()
                )

                val end = Offset(
                    x = (circleRadius * cos(lineInRad) + circleCenter.x).toFloat(),
                    y = (circleRadius * sin(lineInRad) + lineLength + circleCenter.y).toFloat()
                )

                rotate(
                    angleInDegrees + 180,
                    pivot = start
                ) {
                    drawLine(
                        color = Color.Gray,
                        start = start,
                        end = end,
                        strokeWidth = lineThickness.dp.toPx()
                    )
                }

                val clockHands = listOf(ClockHands.Hours, ClockHands.Minutes, ClockHands.Seconds)

                clockHands.forEach { clockHand ->
                    val angleInDegrees = when (clockHand) {
                        ClockHands.Hours -> {
                            (((hours%12)/12f*60f) + minutes/12f) * 360f/60
                        }
                        ClockHands.Minutes -> {
                            (minutes + seconds / 60f) * 360f / 60f
                        }
                        ClockHands.Seconds -> {
                            seconds * 360f / 60f
                        }
                    }

                    val lineLength = when (clockHand) {
                        ClockHands.Hours -> circleRadius*0.5f
                        ClockHands.Minutes -> circleRadius*0.7f
                        ClockHands.Seconds -> circleRadius*0.8f
                    }

                    val lineThickness = when (clockHand) {
                        ClockHands.Hours -> 9f
                        ClockHands.Minutes -> 7f
                        ClockHands.Seconds -> 3f
                    }

                    val start = Offset(
                        x = circleCenter.x,
                        y = circleCenter.y
                    )
                    val end = Offset(
                        x = circleCenter.x,
                        y = circleCenter.y + lineLength
                    )
                    rotate(
                        angleInDegrees - 180,
                        pivot = start
                    ) {
                        drawLine(
                            color = if(clockHand == ClockHands.Seconds) Color.Red else Color.DarkGray,
                            start = start,
                            end = end,
                            strokeWidth = lineThickness.dp.toPx()
                        )
                    }
                }

            }

        }
    }

}

enum class ClockHands {
    Hours,
    Minutes,
    Seconds
}


@Composable
@Preview(showBackground = true)
fun ClockPreview() {

    var  currentTimeInMs by remember {
        mutableStateOf(System.currentTimeMillis())
    }

    LaunchedEffect(key1 = true) {
        while (true) {
            delay(200)
            currentTimeInMs = System.currentTimeMillis()
        }
    }

    Clock(
        circleRadius = 300f,
        outerCircleThickness = 30f,
        time = { currentTimeInMs }
    )
}