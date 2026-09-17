package com.example.pokemondex.features.pokedex.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemondex.core.data.model.EvolutionMember
import com.example.pokemondex.core.data.model.Pokemon
import com.example.pokemondex.core.data.network.PokeApiService
import com.example.pokemondex.core.data.network.model.ChainLinkDto
import kotlinx.coroutines.launch


class PokedexViewModel : ViewModel() {

    // internal list of all loaded pokemon
    private var allLoadedPokemon by mutableStateOf<List<Pokemon>>(emptyList())

    // pagination state
    private val pageSize = 20
    private var currentOffset = 0
    var canLoadMore by mutableStateOf(true)
        private set

    // search and filter state
    var searchQuery by mutableStateOf("")
        private set

    var selectedType by mutableStateOf<String?>(null)
        private set

    // loading states
    var isLoadingInitial by mutableStateOf(false)
        private set
    
    var isFetchingMore by mutableStateOf(false)
        private set

    // computed filtered list
    val filteredPokemonList: List<Pokemon>
        get() {
            return allLoadedPokemon.filter { pokemon ->
                val matchesSearch = pokemon.name.contains(searchQuery, ignoreCase = true) || 
                                   pokemon.id.toString().contains(searchQuery)
                val matchesType = selectedType == null || pokemon.types.contains(selectedType)
                matchesSearch && matchesType
            }
        }

    init {
        fetchNextPage()
    }

    fun updateSearchQuery(query: String) {
        searchQuery = query
    }

    fun updateSelectedType(type: String?) {
        selectedType = if (selectedType == type) null else type
    }

    fun fetchNextPage() {
        if (isFetchingMore || !canLoadMore) return

        viewModelScope.launch {
            if (currentOffset == 0) isLoadingInitial = true else isFetchingMore = true
            
            try {
                // fetch the list for the current page
                val response = PokeApiService.instance.getPokemonList(
                    limit = pageSize, 
                    offset = currentOffset
                )
                
                // fetch details for each new pokemon
                val newItems = response.results.map { entry ->
                    val details = PokeApiService.instance.getPokemonDetails(entry.name)
                    val species = PokeApiService.instance.getPokemonSpecies(details.id)
                    val evolution = PokeApiService.instance.getEvolutionChain(species.evolutionChain.url)
                    
                    val description = species.flavorTextEntries
                        .find { it.language.name == "en" }
                        ?.flavorText
                        ?.replace("\n", " ")
                        ?: "No description available."

                    val category = species.genera
                        .find { it.language.name == "en" }
                        ?.genus
                        ?: "Unknown"

                    val ability = details.abilities
                        .firstOrNull()
                        ?.ability?.name
                        ?.replace("-", " ")
                        ?.replaceFirstChar { it.uppercase() }
                        ?: "None"

                    val hp = details.stats.find { it.stat.name == "hp" }?.baseStat ?: 0
                    val atk = details.stats.find { it.stat.name == "attack" }?.baseStat ?: 0
                    val def = details.stats.find { it.stat.name == "defense" }?.baseStat ?: 0
                    val spatk = details.stats.find { it.stat.name == "special-attack" }?.baseStat ?: 0
                    val spdef = details.stats.find { it.stat.name == "special-defense" }?.baseStat ?: 0
                    val spd = details.stats.find { it.stat.name == "speed" }?.baseStat ?: 0

                    val genNum = species.generation.url.split("/").filter { it.isNotEmpty() }.last().toInt()
                    val gameAppearances = details.gameIndices.map { it.version.name.replace("-", " ").replaceFirstChar { it.uppercase() } }
                    val moves = details.moves.take(15).map { it.move.name.replace("-", " ").replaceFirstChar { it.uppercase() } }
                    
                    val evoChain = flattenEvolutionChain(evolution.chain)

                    Pokemon(
                        id = details.id,
                        name = details.name.replaceFirstChar { it.uppercase() },
                        imageUrl = details.sprites.other.officialArtwork.frontDefault,
                        types = details.types.map { it.type.name.uppercase() },
                        weight = "${details.weight / 10.0} kg",
                        height = "${details.height / 10.0} m",
                        description = description,
                        category = category,
                        ability = ability,
                        hp = hp,
                        atk = atk,
                        def = def,
                        spatk = spatk,
                        spdef = spdef,
                        spd = spd,
                        generation = genNum,
                        color = species.color.name,
                        gameAppearances = gameAppearances,
                        evolutionChain = evoChain,
                        moves = moves
                    )
                }
                
                allLoadedPokemon = allLoadedPokemon + newItems
                currentOffset += pageSize
                
                // stop when reach 151
                if (currentOffset >= 151 || response.results.isEmpty()) {
                    canLoadMore = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoadingInitial = false
                isFetchingMore = false
            }
        }
    }

    //evolution chain
    private fun flattenEvolutionChain(link: ChainLinkDto): List<EvolutionMember> {
        val id = link.species.url.split("/").filter { it.isNotEmpty() }.last().toInt()
        val member = EvolutionMember(
            id = id,
            name = link.species.name.replaceFirstChar { it.uppercase() },
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
        )
        
        val list = mutableListOf(member)
        if (link.evolvesTo.isNotEmpty()) {
            list.addAll(flattenEvolutionChain(link.evolvesTo[0]))
        }
        return list
    }
}
