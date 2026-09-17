package com.example.pokemondex.features.teammaker.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pokemondex.core.data.model.EvolutionMember
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

    // Mock team stats
    val teamHp: Int
        get() = team.count { it != null } * 80 + (if (filledCount > 0) 45 else 0)
    
    val teamAttack: Int
        get() = team.count { it != null } * 95 + (if (filledCount > 0) 30 else 0)
    
    val teamDefense: Int
        get() = team.count { it != null } * 75 + (if (filledCount > 0) 25 else 0)

    init {
        // mocked data for teammaker
        addMockInitialTeam()
    }

    private fun addMockInitialTeam() {
        // mock initial team
        val initial = team.toMutableList()
        initial[0] = Pokemon(
            id = 6, 
            name = "Charizard", 
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/6.png",
            types = listOf("FIRE", "FLYING"),
            weight = "90.5 kg",
            height = "1.7 m",
            description = "Spits fire that is hot enough to melt boulders. Known to cause forest fires unintentionally.",
            category = "Flame Pokémon",
            ability = "Blaze",
            hp = 78, atk = 84, def = 78, spatk = 109, spdef = 85, spd = 100,
            generation = 1,
            color = "red",
            gameAppearances = listOf("Red", "Blue", "Yellow"),
            evolutionChain = listOf(
                EvolutionMember(4, "Charmander", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/4.png"),
                EvolutionMember(5, "Charmeleon", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/5.png"),
                EvolutionMember(6, "Charizard", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/6.png")
            ),
            moves = listOf("Flamethrower", "Fly", "Slash")
        )
        initial[1] = Pokemon(
            id = 25, 
            name = "Pikachu", 
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png",
            types = listOf("ELECTRIC"),
            weight = "6.0 kg",
            height = "0.4 m",
            description = "When several of these POKéMON gather, their electricity could build and cause lightning storms.",
            category = "Mouse Pokémon",
            ability = "Static",
            hp = 35, atk = 55, def = 40, spatk = 50, spdef = 50, spd = 90,
            generation = 1,
            color = "yellow",
            gameAppearances = listOf("Red", "Blue", "Yellow"),
            evolutionChain = listOf(
                EvolutionMember(25, "Pikachu", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png"),
                EvolutionMember(26, "Raichu", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/26.png")
            ),
            moves = listOf("Thunderbolt", "Quick Attack", "Iron Tail")
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
            id = 94, 
            name = "Gengar", 
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/94.png",
            types = listOf("GHOST", "POISON"),
            weight = "40.5 kg",
            height = "1.5 m",
            description = "Under a full moon, this POKéMON likes to mimic the shadows of people and laugh at their fright.",
            category = "Shadow Pokémon",
            ability = "Cursed Body",
            hp = 60, atk = 65, def = 60, spatk = 130, spdef = 75, spd = 110,
            generation = 1,
            color = "purple",
            gameAppearances = listOf("Red", "Blue", "Yellow"),
            evolutionChain = listOf(
                EvolutionMember(92, "Gastly", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/92.png"),
                EvolutionMember(93, "Haunter", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/93.png"),
                EvolutionMember(94, "Gengar", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/94.png")
            ),
            moves = listOf("Shadow Ball", "Hypnosis", "Dream Eater")
        )
        team = newList
    }
}
