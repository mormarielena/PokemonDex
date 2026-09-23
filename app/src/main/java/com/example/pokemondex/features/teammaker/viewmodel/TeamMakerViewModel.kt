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

    // Team stats (Calculated as Averages / Means)
    val teamHp: Int
        get() {
            val active = team.filterNotNull()
            return if (active.isEmpty()) 0 else active.map { it.hp }.average().toInt()
        }
    
    val teamAttack: Int
        get() {
            val active = team.filterNotNull()
            return if (active.isEmpty()) 0 else active.map { it.atk }.average().toInt()
        }
    
    val teamDefense: Int
        get() {
            val active = team.filterNotNull()
            return if (active.isEmpty()) 0 else active.map { it.def }.average().toInt()
        }

    // Predefined popular pokemon to pick from
    val availablePokemon = listOf(
        createMockPokemon(25, "Pikachu", "ELECTRIC", "yellow", 35, 55, 40),
        createMockPokemon(6, "Charizard", "FIRE", "red", 78, 84, 78, "FLYING"),
        createMockPokemon(3, "Venusaur", "GRASS", "green", 80, 82, 83, "POISON"),
        createMockPokemon(9, "Blastoise", "WATER", "blue", 79, 83, 100),
        createMockPokemon(94, "Gengar", "GHOST", "purple", 60, 65, 60, "POISON"),
        createMockPokemon(143, "Snorlax", "NORMAL", "gray", 160, 110, 65),
        createMockPokemon(149, "Dragonite", "DRAGON", "brown", 91, 134, 95, "FLYING"),
        createMockPokemon(150, "Mewtwo", "PSYCHIC", "purple", 106, 110, 90),
        createMockPokemon(130, "Gyarados", "WATER", "blue", 95, 125, 79, "FLYING"),
        createMockPokemon(448, "Lucario", "FIGHTING", "blue", 70, 110, 70, "STEEL")
    )

    fun removePokemon(index: Int) {
        val newList = team.toMutableList()
        newList[index] = null
        team = newList
    }

    fun addPokemonToSlot(index: Int, pokemon: Pokemon) {
        val newList = team.toMutableList()
        newList[index] = pokemon
        team = newList
    }

    private fun createMockPokemon(
        id: Int, name: String, type1: String, color: String, 
        hp: Int, atk: Int, def: Int, type2: String? = null
    ): Pokemon {
        val types = mutableListOf(type1)
        if (type2 != null) types.add(type2)
        
        return Pokemon(
            id = id,
            name = name,
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png",
            types = types,
            weight = "Unknown",
            height = "Unknown",
            description = "A powerful $name.",
            category = "Unknown",
            ability = "Unknown",
            hp = hp, atk = atk, def = def, spatk = 50, spdef = 50, spd = 50,
            generation = 1,
            color = color,
            gameAppearances = listOf(),
            evolutionChain = listOf(),
            moves = listOf()
        )
    }
}
