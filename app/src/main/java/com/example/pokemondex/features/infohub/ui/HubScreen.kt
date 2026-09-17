package com.example.pokemondex.features.infohub.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.pokemondex.core.ui.components.Accordion
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
            .background(Color(0xFFF2F2F2))
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Surface(
            color = Color.White,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Info Hub", fontSize = 28.sp, fontWeight = FontWeight.Black)
                Text(text = "Explore the Pokemon World!", fontSize = 14.sp, color = Color.Gray)
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

            Spacer(modifier = Modifier.height(16.dp))

            // Favorites (Mock data for now)
            SectionContainer(title = "FAVORITES") {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val favorites = listOf(25 to "Pikachu", 6 to "Charizard", 149 to "Dragonite")
                    items(favorites) { (id, name) ->
                        FavoriteMiniCard(id, name)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Saved Teams Accordion
            Accordion(title = "SAVED TEAMS", initialOpen = false) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    MockTeamItem(name = "Dream Team #1", count = 6)
                    MockTeamItem(name = "Kanto Starters", count = 3)
                }
            }
        }
    }
}

@Composable
fun MockTeamItem(name: String, count: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF9FAFB), RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFFFFE4E6), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Star, contentDescription = null, tint = Color.Red, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = "$count Pokémon", fontSize = 12.sp, color = Color.Gray)
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

@Composable
fun SectionContainer(title: String, content: @Composable () -> Unit) {
    Column {
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Black,
            color = Color.Gray,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
        )
        content()
    }
}

//to-do when initialize room database
@Composable
fun FavoriteMiniCard(id: Int, name: String) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png",
            contentDescription = name,
            modifier = Modifier.size(60.dp)
        )
        Text(text = name, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}
