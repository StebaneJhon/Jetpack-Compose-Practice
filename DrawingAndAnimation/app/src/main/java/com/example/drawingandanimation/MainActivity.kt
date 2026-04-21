package com.example.drawingandanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.drawingandanimation.ui.theme.DrawingAndAnimationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DrawingAndAnimationTheme {

                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CustomCircularProgressIndicator(
                        modifier = Modifier.size(250.dp),
                        initialValue = 50,
                        primaryColor = Color.Blue,
                        secondaryColor = Color.Gray,
                        circleRadius = 230f,
                        onPositionChange = {

                        }
                    )
                }

            }
        }
    }
}



@Composable
fun MyCanvas() {
    Canvas(modifier = Modifier.padding(20.dp).size(300.dp)) {
        drawRect(
            color = Color.Gray,
            size = size
        )

        drawRect(
            color = Color.Yellow,
            topLeft = Offset(50.dp.toPx(), 50.dp.toPx()),
            size = Size(200.dp.toPx(), 200.dp.toPx())
        )

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color.Blue, Color.Cyan),
                center = center,
                radius = 100.dp.toPx()
            ),
            radius = 75.dp.toPx()
        )

        drawArc(
            color = Color.Green,
            startAngle = 0f,
            sweepAngle = 300f,
            useCenter = true,
            topLeft = Offset(50.dp.toPx(), 5.dp.toPx()),
            size = Size(45.dp.toPx(), 45.dp.toPx())
        )

    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DrawingAndAnimationTheme {
        MyCanvas()
    }
}

