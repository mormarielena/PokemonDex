package com.example.pokemondex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pokemondex.core.data.model.Pokemon
import com.example.pokemondex.features.infohub.ui.Category
import com.example.pokemondex.features.infohub.ui.HubDetailScreen
import com.example.pokemondex.features.infohub.ui.HubScreen
import com.example.pokemondex.features.pokedex.ui.PokedexListScreen
import com.example.pokemondex.features.pokedex.ui.PokemonDetailScreen
import com.example.pokemondex.features.teammaker.ui.TeamMakerScreen
import com.example.pokemondex.ui.theme.PokemonDexTheme

enum class Screen {
    Pokedex, InfoHub, TeamMaker
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokemonDexTheme {
                // navigational variables
                var currentScreen by remember { mutableStateOf(Screen.Pokedex) }
                var selectedPokemon by remember { mutableStateOf<Pokemon?>(null) }
                var selectedCategory by remember { mutableStateOf<Category?>(null) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        // show bottom bar only on pokedex screen
                        if (selectedPokemon == null && selectedCategory == null) {
                            NavigationBar(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                                    .border(
                                        width = 1.5.dp,
                                        color = MaterialTheme.colorScheme.tertiary,
                                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                                    )
                            ) {
                                NavigationBarItem(
                                    selected = currentScreen == Screen.Pokedex,
                                    onClick = { currentScreen = Screen.Pokedex },
                                    label = { Text("Pokédex") },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.pokedex_icon),
                                            contentDescription = null,
                                            modifier = Modifier.size(24.dp)
                                        )


                                    }
                                )
                                NavigationBarItem(
                                    selected = currentScreen == Screen.TeamMaker,
                                    onClick = { currentScreen = Screen.TeamMaker },
                                    label = { Text("Team") },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.pokemon_trainer),
                                            contentDescription = null,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                )
                                NavigationBarItem(
                                    selected = currentScreen == Screen.InfoHub,
                                    onClick = { currentScreen = Screen.InfoHub },
                                    label = { Text("Hub") },


                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.infohub_icon),
                                            contentDescription = null,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        when (currentScreen) {
                            Screen.Pokedex -> {
                                if (selectedPokemon == null) {
                                    PokedexListScreen(onPokemonClick = { selectedPokemon = it })
                                } else {
                                    PokemonDetailScreen(
                                        pokemon = selectedPokemon!!,
                                        onBackClick = { selectedPokemon = null }
                                    )
                                    BackHandler { selectedPokemon = null }
                                }
                            }
                            Screen.TeamMaker -> {
                                TeamMakerScreen()
                            }
                            Screen.InfoHub -> {
                                if (selectedCategory == null) {
                                    HubScreen(onCategoryClick = { selectedCategory = it })
                                } else {
                                    HubDetailScreen(
                                        category = selectedCategory!!,
                                        onBack = { selectedCategory = null }
                                    )
                                    BackHandler { selectedCategory = null }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
