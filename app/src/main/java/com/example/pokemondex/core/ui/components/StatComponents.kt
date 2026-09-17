package com.example.pokemondex.core.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

//stats bar indicator for the stats section
@Composable
fun StatBar(label: String, value: Int, max: Int = 255) {
    val progress = (value.toFloat() / max).coerceIn(0f, 1f)
    val barColor = when {
        value >= 100 -> Color(0xFF5CB85C) // Green
        value >= 60 -> Color(0xFFF5C518)  // Yellow
        else -> Color(0xFFFF6B35)         // Red
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier.width(48.dp),
            fontSize = 12.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value.toString(),
            modifier = Modifier.width(36.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = barColor,
            trackColor = Color.LightGray.copy(alpha = 0.3f),
        )
    }
}

//generated graph for the stats section

@Composable
fun RadarChart(
    stats: List<Int>,
    labels: List<String>,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(220.dp)) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = size.width / 2 * 0.7f
            val angleStep = (2 * Math.PI / stats.size).toFloat()

            // Draw grid levels (polygons)
            val levels = listOf(0.33f, 0.66f, 1.0f)
            levels.forEach { level ->
                val path = Path()
                for (i in stats.indices) {
                    val angle = i * angleStep - (Math.PI / 2).toFloat()
                    val x = center.x + radius * level * cos(angle)
                    val y = center.y + radius * level * sin(angle)
                    if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                }
                path.close()
                drawPath(path, Color(0xFFE5E7EB), style = Stroke(width = 1.dp.toPx()))
            }

            // Draw axis lines
            for (i in stats.indices) {
                val angle = i * angleStep - (Math.PI / 2).toFloat()
                val x = center.x + radius * cos(angle)
                val y = center.y + radius * sin(angle)
                drawLine(Color(0xFFE5E7EB), center, Offset(x, y), strokeWidth = 1.dp.toPx())
            }

            // Draw data polygon
            val statPath = Path()
            for (i in stats.indices) {
                val angle = i * angleStep - (Math.PI / 2).toFloat()
                val statRadius = radius * (stats[i].toFloat() / 255f).coerceIn(0f, 1f)
                val x = center.x + statRadius * cos(angle)
                val y = center.y + statRadius * sin(angle)
                if (i == 0) statPath.moveTo(x, y) else statPath.lineTo(x, y)
            }
            statPath.close()
            drawPath(statPath, color.copy(alpha = 0.2f), style = Fill)
            drawPath(statPath, color, style = Stroke(width = 2.dp.toPx()))

            // Draw labels
            val paint = android.graphics.Paint().apply {
                this.color = android.graphics.Color.GRAY
                this.textSize = 10.sp.toPx()
                this.textAlign = android.graphics.Paint.Align.CENTER
                this.isFakeBoldText = true
            }
            for (i in labels.indices) {
                val angle = i * angleStep - (Math.PI / 2).toFloat()
                val x = center.x + (radius + 20.dp.toPx()) * cos(angle)
                val y = center.y + (radius + 10.dp.toPx()) * sin(angle)
                drawContext.canvas.nativeCanvas.drawText(labels[i], x, y, paint)
            }
        }
    }
}
