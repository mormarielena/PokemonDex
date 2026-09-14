package com.example.pokemondex.features.pokedex.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokemondex.core.data.model.Pokemon
import com.example.pokemondex.core.ui.components.DexCard
import com.example.pokemondex.core.ui.components.TypeBadge
import com.example.pokemondex.core.ui.components.getTypeColor
import com.example.pokemondex.features.pokedex.viewmodel.PokedexViewModel

@Composable
fun PokedexListScreen(
    onPokemonClick: (Pokemon) -> Unit,
    viewModel: PokedexViewModel = viewModel()
) {
    val pokemonList = viewModel.pokemonList
    val isLoading = viewModel.isLoading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Titlu
        Text(
            text = "Pokédex",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        
        SearchBar()
        
        Spacer(modifier = Modifier.height(16.dp))
        FilterChips()
        
        Spacer(modifier = Modifier.height(16.dp))
        
        if (isLoading && pokemonList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.Red)
            }
        } else {
            PokemonList(pokemonList, onPokemonClick)
        }
    }
}

// TO-DO search functionality
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var text by remember { mutableStateOf("") }
    
    TextField(
        value = text,
        onValueChange = { text = it },
        placeholder = { Text("Search by name or number...", color = Color.Gray) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon", tint = Color.Gray) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp)),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF2F2F2),
            unfocusedContainerColor = Color(0xFFF2F2F2),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

// TO-DO clickable categories
@Composable
fun FilterChips() {
    val filters = listOf("Gen 1", "Gen 2", "Gen 3", "Dark", "Dragon", "Electric", "Fairy")
    
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(filters) { filter ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFF2F2F2))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(text = filter, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
            }
        }
    }
}

@Composable
fun PokemonList(
    pokemonList: List<Pokemon>,
    onPokemonClick: (Pokemon) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(pokemonList) { pokemon ->
            PokemonCard(pokemon, onPokemonClick)
        }
    }
}

@Composable
fun PokemonCard(
    pokemon: Pokemon,
    onPokemonClick: (Pokemon) -> Unit
) {
    DexCard(
        title = pokemon.name,
        subtitle = "#${pokemon.id}",
        imageUrl = pokemon.imageUrl,
        accentColor = getTypeColor(pokemon.types.first()),
        onClick = { onPokemonClick(pokemon) }
    ) {
        pokemon.types.forEach { type ->
            TypeBadge(type = type)
        }
    }
}
