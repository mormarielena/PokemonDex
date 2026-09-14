package com.example.pokemondex.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TypeBadge(type: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(getTypeColor(type))
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(text = type, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
    }
}

fun getTypeColor(type: String): Color {
    return when (type.uppercase()) {
        "GRASS" -> Color(0xFF63BC5A)
        "POISON" -> Color(0xFFB567CE)
        "FIRE" -> Color(0xFFFF9D55)
        "WATER" -> Color(0xFF5090D6)
        else -> Color.Gray
    }
}
