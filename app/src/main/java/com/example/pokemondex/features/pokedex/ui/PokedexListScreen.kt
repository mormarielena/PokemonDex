package com.example.pokemondex.features.pokedex.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.pokemondex.R
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
    val pokemonList = viewModel.filteredPokemonList
    val isLoadingInitial = viewModel.isLoadingInitial

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Title Image
        Image(
            painter = painterResource(id = R.drawable.pokedex_title),
            contentDescription = "Pokédex",
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .height(48.dp)
        )
        
        SearchBar(
            query = viewModel.searchQuery,
            onQueryChange = { viewModel.updateSearchQuery(it) }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        FilterChips(
            selectedType = viewModel.selectedType,
            onTypeClick = { viewModel.updateSelectedType(it) }
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        //load more pokemons when reached bottom of initial list load
        if (isLoadingInitial && pokemonList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.Red)
            }
        } else {
            PokemonList(
                pokemonList = pokemonList, 
                onPokemonClick = onPokemonClick,
                isFetchingMore = viewModel.isFetchingMore,
                canLoadMore = viewModel.canLoadMore,
                onLoadMore = { viewModel.fetchNextPage() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Search by name or number...", color = Color.Gray) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon", tint = Color.Gray) },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Color.Gray)
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(1.5.dp, MaterialTheme.colorScheme.tertiary, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp)),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF2F2F2),
            unfocusedContainerColor = Color(0xFFF2F2F2),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = true
    )
}

@Composable
fun FilterChips(
    selectedType: String?,
    onTypeClick: (String) -> Unit
) {
    val types = listOf(
        "Normal", "Fire", "Water", "Grass", "Electric", "Ice", 
        "Fighting", "Poison", "Ground", "Flying", "Psychic", 
        "Bug", "Rock", "Ghost", "Dragon", "Dark", "Steel", "Fairy"
    )
    
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(types) { type ->
            val isSelected = type.uppercase() == selectedType
            val chipColor = if (isSelected) getTypeColor(type) else Color(0xFFF2F2F2)
            val textColor = if (isSelected) Color.White else Color.DarkGray
            
            Box(
                modifier = Modifier
                    .border(1.5.dp, MaterialTheme.colorScheme.tertiary, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(chipColor)
                    .clickable { onTypeClick(type.uppercase()) }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = type, 
                    fontSize = 14.sp, 
                    fontWeight = FontWeight.Bold, 
                    color = textColor
                )
            }
        }
    }
}

@Composable
fun PokemonList(
    pokemonList: List<Pokemon>,
    onPokemonClick: (Pokemon) -> Unit,
    isFetchingMore: Boolean,
    canLoadMore: Boolean,
    onLoadMore: () -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(pokemonList) { pokemon ->
            // Trigger load more when near the bottom
            if (pokemon == pokemonList.last() && canLoadMore && !isFetchingMore) {
                LaunchedEffect(Unit) {
                    onLoadMore()
                }
            }
            PokemonCard(pokemon, onPokemonClick)
        }

        // Show spinner at the bottom if we are loading more
        if (isFetchingMore) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.Red)
                }
            }
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
        subtitle = "#${pokemon.id.toString().padStart(3, '0')}",
        imageUrl = pokemon.imageUrl,
        accentColor = getTypeColor(pokemon.types.first()),
        onClick = { onPokemonClick(pokemon) }
    ) {
        pokemon.types.forEach { type ->
            TypeBadge(type = type)
        }
    }
}
