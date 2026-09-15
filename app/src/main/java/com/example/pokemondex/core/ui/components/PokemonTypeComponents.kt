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
        "FIRE" -> Color(0xFFFF9D55)
        "WATER" -> Color(0xFF5090D6)
        "GRASS" -> Color(0xFF63BC5A)
        "ELECTRIC" -> Color(0xFFF4D23C)
        "FLYING" -> Color(0xFF89AAE3)
        "DRAGON" -> Color(0xFF0C69C8)
        "POISON" -> Color(0xFFB567CE)
        "GHOST" -> Color(0xFF5269AC)
        "DARK" -> Color(0xFF5A5366)
        "FAIRY" -> Color(0xFFEC8FE6)
        "PSYCHIC" -> Color(0xFFFA7179)
        "ICE" -> Color(0xFF74CEC0)
        "ROCK" -> Color(0xFFC7B78B)
        "GROUND" -> Color(0xFFE2D291)
        "STEEL" -> Color(0xFF5A8EA1)
        "FIGHTING" -> Color(0xFFCE4069)
        "BUG" -> Color(0xFF90C12C)
        "NORMAL" -> Color(0xFF9099A1)
        else -> Color.Gray
    }
}
