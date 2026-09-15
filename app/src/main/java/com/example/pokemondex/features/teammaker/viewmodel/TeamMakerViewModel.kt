package com.example.pokemondex.features.teammaker.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pokemondex.core.data.model.Pokemon


class TeamMakerViewModel : ViewModel() {

    // 6 slot list
    var team by mutableStateOf<List<Pokemon?>>(List(6) { null })
        private set

    // occupied slots
    val filledCount: Int
        get() = team.count { it != null }

    // current team types
    val uniqueTypes: List<String>
        get() = team.filterNotNull().flatMap { it.types }.distinct()

    init {
        // mocked data for teammaker
        addMockInitialTeam()
    }

    private fun addMockInitialTeam() {
        // mock initial team
        val initial = team.toMutableList()
        initial[0] = Pokemon(
            id = "006", 
            name = "Charizard", 
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/6.png",
            types = listOf("FIRE", "FLYING"),
            weight = "90.5 kg",
            height = "1.7 m",
            description = ""
        )
        initial[1] = Pokemon(
            id = "025", 
            name = "Pikachu", 
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png",
            types = listOf("ELECTRIC"),
            weight = "6.0 kg",
            height = "0.4 m",
            description = ""
        )
        team = initial
    }

    fun removePokemon(index: Int) {
        val newList = team.toMutableList()
        newList[index] = null
        team = newList
    }

    fun addPokemon(index: Int) {
        // selection; mocked a pokemon
        val newList = team.toMutableList()
        newList[index] = Pokemon(
            id = "094", 
            name = "Gengar", 
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/94.png",
            types = listOf("GHOST", "POISON"),
            weight = "40.5 kg",
            height = "1.5 m",
            description = ""
        )
        team = newList
    }
}
