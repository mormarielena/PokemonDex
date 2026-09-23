package com.example.pokemondex.features.infohub.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokemondex.R
import com.example.pokemondex.features.infohub.viewmodel.HubViewModel

data class Category(
    val key: String,
    val label: String,
    val icon: ImageVector,
    val color: Color,
    val bg: Color
)

@Composable
fun HubScreen(
    onCategoryClick: (Category) -> Unit,
    viewModel: HubViewModel = viewModel()
) {
    val stats = viewModel.stats
    
    val categories = listOf(
        Category("berries", "Berries", Icons.Default.ShoppingCart, Color(0xFFF08030), Color(0xFFFFF3E0)),
        Category("items", "Items", Icons.Default.Build, Color(0xFF6890F0), Color(0xFFE8EAF6)),
        Category("places", "Locations", Icons.Default.LocationOn, Color(0xFF78C850), Color(0xFFE8F5E9))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.infohub_title),
                    contentDescription = "InfoHub",
                    modifier = Modifier
                        .padding(horizontal = 0.dp, vertical = 8.dp)
                        .height(38.dp)
                )
                Text(
                    text = "Explore the Pokemon World!", 
                    fontSize = 14.sp, 
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            // Category Cards
            categories.forEach { cat ->
                val count = when(cat.key) {
                    "berries" -> stats.berryCount
                    "items" -> stats.itemCount
                    else -> stats.locationCount
                }
                
                CategoryItem(
                    category = cat,
                    count = count,
                    onClick = { onCategoryClick(cat) }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun CategoryItem(category: Category, count: String, onClick: () -> Unit) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(category.bg, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(category.icon, contentDescription = null, tint = category.color)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = category.label, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = count, fontSize = 12.sp, color = Color.Gray)
            }
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.LightGray)
        }
    }
}
