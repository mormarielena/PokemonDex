package com.example.pokemondex.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

/**
 * Reusabe card for basic info used both in Pokedex list and Hub list
 */
@Composable
fun DexCard(
    title: String,
    subtitle: String,
    imageUrl: String,
    accentColor: Color = Color(0xFFF0F0F0),
    showAccentBar: Boolean = true,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // color coded accent
            if (showAccentBar) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(8.dp)
                        .background(accentColor)
                )
            }
            
            // sprites/icons
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                modifier = Modifier
                    .padding(8.dp)
                    .size(80.dp)
            )
            
            // main information
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = subtitle, 
                    color = Color.Gray, 
                    fontSize = 12.sp, 
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = title, 
                    fontSize = 18.sp, 
                    fontWeight = FontWeight.ExtraBold
                )
                
                // extra space for types
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp), 
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    content()
                }
            }
            
            // right arrow
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(40.dp)
                    .background(Color(0xFFF0F0F0)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight, 
                    contentDescription = null, 
                    tint = Color.Gray
                )
            }
        }
    }
}
