package com.example.pokemondex.features.pokedex.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pokemondex.core.data.model.Pokemon
import com.example.pokemondex.core.ui.components.RadarChart
import com.example.pokemondex.core.ui.components.StatBar
import com.example.pokemondex.core.ui.components.TypeBadge
import com.example.pokemondex.core.ui.components.getTypeColor

@Composable
fun PokemonDetailScreen(pokemon: Pokemon, onBackClick: () -> Unit) {
    var selectedTab by remember { mutableStateOf("Info") }
    val primaryColor = getTypeColor(pokemon.types.firstOrNull() ?: "")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
    ) {
        // header section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(primaryColor, primaryColor.copy(alpha = 0.7f)),
                        startY = 0f,
                        endY = 1000f
                    )
                )
                .padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 0.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "#${pokemon.id.toString().padStart(3, '0')}", 
                        color = Color.White.copy(alpha = 0.6f),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
                
                Text(
                    text = pokemon.name,
                    color = Color.White,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Black
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    pokemon.types.forEach { type ->
                        Box(
                            modifier = Modifier
                                .background(Color.White.copy(alpha = 0.25f), RoundedCornerShape(20.dp))
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = type.uppercase(),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }
                    }
                }

                AsyncImage(
                    model = pokemon.imageUrl,
                    contentDescription = pokemon.name,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .size(170.dp)
                )
            }
        }

        // tab row for info and stats
        Surface(shadowElevation = 2.dp) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                listOf("Info", "Stats", "History", "Attacks").forEach { tab ->
                    val isSelected = selectedTab == tab
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextButton(
                            onClick = { selectedTab = tab },
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(vertical = 12.dp)
                        ) {
                            Text(
                                text = tab.uppercase(),
                                color = if (isSelected) primaryColor else Color.LightGray,
                                fontWeight = FontWeight.Black,
                                fontSize = 10.sp,
                                letterSpacing = 1.sp
                            )
                        }
                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .height(2.dp)
                                    .fillMaxWidth(0.6f)
                                    .background(primaryColor, RoundedCornerShape(topStart = 2.dp, topEnd = 2.dp))
                            )
                        }
                    }
                }
            }
        }

        // content area
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                when (selectedTab) {
                    "Info" -> PokemonInfoTab(pokemon)
                    "Stats" -> PokemonStatsTab(pokemon)
                    "History" -> PokemonHistoryTab(pokemon, primaryColor)
                    "Attacks" -> PokemonAttacksTab(pokemon)
                }
            }
        }
    }
}

@Composable
fun PokemonInfoTab(pokemon: Pokemon) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("About", fontWeight = FontWeight.Black, fontSize = 16.sp, color = Color.DarkGray)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = pokemon.description, 
                    fontSize = 14.sp, 
                    color = Color.Gray,
                    lineHeight = 22.sp
                )
            }
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                val attributes = listOf(
                    "Height" to pokemon.height,
                    "Weight" to pokemon.weight,
                    "Category" to pokemon.category,
                    "Gen" to "Gen ${pokemon.generation}",
                    "Ability" to pokemon.ability,
                    "Color" to pokemon.color.replaceFirstChar { it.uppercase() }
                )
                
                attributes.chunked(2).forEach { row ->
                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                        row.forEach { (label, value) ->
                            Column(modifier = Modifier.weight(1f)) {
                                Text(label, fontSize = 11.sp, color = Color.LightGray, fontWeight = FontWeight.Bold)
                                Text(value, fontWeight = FontWeight.Black, fontSize = 15.sp, color = Color.DarkGray)
                            }
                        }
                    }
                }
            }
        }

        if (pokemon.evolutionChain.size > 1) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Evolution Chain", fontWeight = FontWeight.Black, fontSize = 16.sp, color = Color.DarkGray)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        pokemon.evolutionChain.forEachIndexed { index, member ->
                            if (index > 0) {
                                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                AsyncImage(model = member.imageUrl, contentDescription = null, modifier = Modifier.size(50.dp))
                                Text(member.name, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonStatsTab(pokemon: Pokemon) {
    val stats = listOf(pokemon.hp, pokemon.atk, pokemon.def, pokemon.spatk, pokemon.spdef, pokemon.spd)
    val labels = listOf("HP", "ATK", "DEF", "SpA", "SpD", "SPE")
    val totalStats = stats.sum()
    val primaryColor = getTypeColor(pokemon.types.firstOrNull() ?: "")

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Base Stats", fontWeight = FontWeight.Black, fontSize = 16.sp, color = Color.DarkGray)
                    Text("Total: $totalStats", color = Color.Gray, fontWeight = FontWeight.Black, fontSize = 13.sp)
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                labels.forEachIndexed { i, label ->
                    StatBar(label = label, value = stats[i])
                }
            }
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier.padding(20.dp)) {
                RadarChart(stats = stats, labels = labels, color = primaryColor)
            }
        }
    }
}

@Composable
fun PokemonHistoryTab(pokemon: Pokemon, color: Color) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text("Game Appearances", fontWeight = FontWeight.Black, fontSize = 16.sp, color = Color.DarkGray)
            Spacer(modifier = Modifier.height(16.dp))
            pokemon.gameAppearances.forEach { game ->
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
                    Box(modifier = Modifier.size(6.dp).background(color, RoundedCornerShape(3.dp)))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(game, fontSize = 14.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

@Composable
fun PokemonAttacksTab(pokemon: Pokemon) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text("Move Set", fontWeight = FontWeight.Black, fontSize = 16.sp, color = Color.DarkGray)
            Spacer(modifier = Modifier.height(16.dp))
            pokemon.moves.forEach { move ->
                Row(
                    verticalAlignment = Alignment.CenterVertically, 
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .background(Color(0xFFF9FAFB), RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Text(move, fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color.DarkGray)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}
