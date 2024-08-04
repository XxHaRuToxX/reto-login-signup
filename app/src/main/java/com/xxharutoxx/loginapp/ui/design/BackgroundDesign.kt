package com.xxharutoxx.loginapp.ui.design

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

//@Preview(showBackground = true)
@Composable
fun BackgroundDesign(height:Dp){
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(height)
    ) {
        val wavePath = Path().apply {
//            moveTo(0f, size.height / 2) // Start point
            moveTo(0f, 0f) // Start point

            var x = 0f
            val waveWidth = 2000f
            val waveHeight = -80f
            val waveCount = 50

            while (x <= size.width) {
                val y = size.height / 2 + waveHeight * kotlin.math.sin(4 * Math.PI * x / waveWidth).toFloat()
                lineTo(x, y)
                x += waveWidth / waveCount
            }
            translate(offset = Offset(0f, 100f))

            lineTo(0f, -1000000f)
            close()
        }
        drawPath(
            path = wavePath,
            color = Color(0xFF585E97),
            style = Fill
        )
    }
}