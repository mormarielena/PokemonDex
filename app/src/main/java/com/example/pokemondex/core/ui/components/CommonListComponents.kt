package com.example.pokemondex.core.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

//reusable accordion component
@Composable
fun Accordion(
    title: String,
    initialOpen: Boolean = true,
    content: @Composable () -> Unit
) {
    var isOpen by remember { mutableStateOf(initialOpen) }

    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalDivider(color = Color(0xFFF3F4F6))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isOpen = !isOpen }
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title, 
                fontWeight = FontWeight.Black, 
                fontSize = 14.sp, 
                color = Color.DarkGray, 
                letterSpacing = 1.sp
            )
            Icon(
                imageVector = if (isOpen) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "Toggle",
                tint = Color.Gray
            )
        }
        AnimatedVisibility(visible = isOpen) {
            Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                content()
            }
        }
    }
}

//reusable card component with types
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
