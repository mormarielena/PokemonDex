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
    val backgroundColor = getTypeColor(type)
    
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor.copy(alpha = 0.2f))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = type.uppercase(),
            color = backgroundColor,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}

fun getTypeColor(type: String): Color {

    return when (type.lowercase()) {
        "fire" -> Color(0xFFFF6B35)
        "water" -> Color(0xFF4A90E2)
        "grass" -> Color(0xFF5CB85C)
        "electric" -> Color(0xFFF5C518)
        "psychic" -> Color(0xFFE83E8C)
        "poison" -> Color(0xFF9B59B6)
        "flying" -> Color(0xFF89AAE3)
        "dragon" -> Color(0xFF7B68EE)
        "ghost" -> Color(0xFF8E44AD)
        "dark" -> Color(0xFF343A40)
        "fairy" -> Color(0xFFFF85C2)
        "ice" -> Color(0xFF5BC0DE)
        "rock" -> Color(0xFFBDC3C7)
        "ground" -> Color(0xFFE59866)
        "steel" -> Color(0xFF85929E)
        "fighting" -> Color(0xFFC0392B)
        "bug" -> Color(0xFF82C341)
        "normal" -> Color(0xFFAAA9A5)
        else -> Color(0xFFAAA9A5)
    }
}
