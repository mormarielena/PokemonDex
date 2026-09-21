package com.example.pokemondex.features.teammaker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.pokemondex.core.data.model.Pokemon
import com.example.pokemondex.core.ui.components.Accordion
import com.example.pokemondex.core.ui.components.DexCard
import com.example.pokemondex.core.ui.components.TypeBadge
import com.example.pokemondex.core.ui.components.getTypeColor
import com.example.pokemondex.features.teammaker.viewmodel.TeamMakerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamMakerScreen(
    viewModel: TeamMakerViewModel = viewModel()
) {
    val team = viewModel.team
    val filledCount = viewModel.filledCount
    val uniqueTypes = viewModel.uniqueTypes
    
    // UI state for bottom sheet
    var showSelectionSheet by remember { mutableStateOf(false) }
    var selectedSlotIndex by remember { mutableStateOf(0) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        // Title
        Column(modifier = Modifier.padding(20.dp)) {
            Text(text = "Team Simulator", fontSize = 28.sp, fontWeight = FontWeight.Black, color = Color.Black)
            Text(
                text = "$filledCount/6 Pokémon selected", 
                fontSize = 14.sp, 
                color = Color.Gray, 
                fontWeight = FontWeight.Medium
            )
        }

        // card container for pokemon
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            team.chunked(3).forEachIndexed { rowIndex, rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowItems.forEachIndexed { colIndex, pokemon ->
                        val actualIndex = rowIndex * 3 + colIndex
                        
                        Box(modifier = Modifier.weight(1f).aspectRatio(0.8f)) {
                            if (pokemon != null) {
                                FilledSlot(
                                    pokemon = pokemon,
                                    onRemove = { viewModel.removePokemon(actualIndex) }
                                )
                            } else {
                                EmptySlot(
                                    onAdd = { 
                                        selectedSlotIndex = actualIndex
                                        showSelectionSheet = true 
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // synergy accordion container
        Accordion(title = "TEAM SYNERGY") {
            if (uniqueTypes.isEmpty()) {
                Text("Add Pokémon to see type synergy", color = Color.Gray, fontSize = 14.sp)
            } else {
                @OptIn(ExperimentalLayoutApi::class)
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    uniqueTypes.forEach { type ->
                        TypeBadge(type = type)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Team stats accordion - NOW SHOWING AVERAGES
        Accordion(title = "AVERAGE STATS", initialOpen = false) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                if (filledCount == 0) {
                    Text("Add Pokémon to see average team stats", color = Color.Gray, fontSize = 14.sp)
                } else {
                    StatRow(label = "Mean HP", value = viewModel.teamHp.toString(), color = Color(0xFF4CAF50))
                    StatRow(label = "Mean Attack", value = viewModel.teamAttack.toString(), color = Color(0xFFF44336))
                    StatRow(label = "Mean Defense", value = viewModel.teamDefense.toString(), color = Color(0xFF2196F3))
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
    }
    
    // selection sheet
    if (showSelectionSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSelectionSheet = false },
            containerColor = Color.White
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Choose a Pokémon",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(viewModel.availablePokemon) { pkm ->
                        DexCard(
                            title = pkm.name,
                            subtitle = "Lv 50",
                            imageUrl = pkm.imageUrl,
                            accentColor = getTypeColor(pkm.types.first()),
                            showAccentBar = true,
                            onClick = {
                                viewModel.addPokemonToSlot(selectedSlotIndex, pkm)
                                showSelectionSheet = false
                            }
                        ) {
                            pkm.types.forEach { type -> TypeBadge(type) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatRow(label: String, value: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(color, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Black)
        }
    }
}

@Composable
fun FilledSlot(pokemon: Pokemon, onRemove: () -> Unit) {
    val bgColor = getTypeColor(pokemon.types.first()).copy(alpha = 0.15f)
    val borderColor = getTypeColor(pokemon.types.first()).copy(alpha = 0.3f)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .border(1.5.dp, borderColor, RoundedCornerShape(16.dp))
    ) {
        // delete button
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(6.dp)
                .size(24.dp)
                .background(Color.Red, CircleShape)
                .clickable { onRemove() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Clear, 
                contentDescription = "Remove", 
                tint = Color.White, 
                modifier = Modifier.size(14.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = pokemon.imageUrl,
                contentDescription = pokemon.name,
                modifier = Modifier.size(56.dp)
            )
            Text(
                text = pokemon.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                pokemon.types.forEach { type ->
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(getTypeColor(type), CircleShape)
                    )
                }
            }
        }
    }
}

@Composable
fun EmptySlot(onAdd: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFF9FAFB))
            .border(2.dp, Color(0xFFE5E7EB), RoundedCornerShape(16.dp))
            .clickable { onAdd() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.Add, 
            contentDescription = "Add", 
            tint = Color.LightGray, 
            modifier = Modifier.size(28.dp)
        )
        Text("Add", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.LightGray)
    }
}
