package com.example.pokemondex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.pokemondex.core.data.model.Pokemon
import com.example.pokemondex.features.infohub.ui.Category
import com.example.pokemondex.features.infohub.ui.HubDetailScreen
import com.example.pokemondex.features.infohub.ui.HubScreen
import com.example.pokemondex.features.pokedex.ui.PokedexListScreen
import com.example.pokemondex.features.pokedex.ui.PokemonDetailScreen
import com.example.pokemondex.ui.theme.PokemonDexTheme

enum class Screen {
    Pokedex, InfoHub
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
                            NavigationBar {
                                NavigationBarItem(
                                    selected = currentScreen == Screen.Pokedex,
                                    onClick = { currentScreen = Screen.Pokedex },
                                    label = { Text("Pokédex") },
                                    icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) }
                                )
                                NavigationBarItem(
                                    selected = currentScreen == Screen.InfoHub,
                                    onClick = { currentScreen = Screen.InfoHub },
                                    label = { Text("Hub") },
                                    icon = { Icon(Icons.Default.Info, contentDescription = null) }
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
