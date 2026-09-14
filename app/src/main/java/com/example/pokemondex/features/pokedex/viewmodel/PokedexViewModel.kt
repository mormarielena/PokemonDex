package com.example.pokemondex.features.pokedex.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemondex.core.data.model.Pokemon
import com.example.pokemondex.core.data.network.PokeApiService
import kotlinx.coroutines.launch


class PokedexViewModel : ViewModel() {

    // list of pokemon
    var pokemonList by mutableStateOf<List<Pokemon>>(emptyList())
        private set

    // load pokemon from API
    var isLoading by mutableStateOf(false)
        private set

    init {
        loadPokemon()
    }

    private fun loadPokemon() {
        viewModelScope.launch {
            isLoading = true
            try {
                // we initialize 20 pokemons from the api
                val response = PokeApiService.instance.getPokemonList(limit = 20)
                
                // we extract the details for each
                val detailedList = response.results.map { entry ->
                    val details = PokeApiService.instance.getPokemonDetails(entry.name)
                    val species = PokeApiService.instance.getPokemonSpecies(details.id)
                    
                    // we extract the english description
                    val description = species.flavorTextEntries
                        .find { it.language.name == "en" }
                        ?.flavorText
                        ?.replace("\n", " ") // clean text
                        ?: "No description available."

                    Pokemon(
                        id = String.format("%03d", details.id),
                        name = details.name.replaceFirstChar { it.uppercase() },
                        imageUrl = details.sprites.other.officialArtwork.frontDefault,
                        types = details.types.map { it.type.name.uppercase() },
                        weight = "${details.weight / 10.0} kg",
                        height = "${details.height / 10.0} m",
                        description = description
                    )
                }
                
                pokemonList = detailedList
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }
}
