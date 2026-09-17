package com.example.pokemondex.core.data.model

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<String>,
    val weight: String,
    val height: String,
    val description: String,
    val category: String,
    val ability: String,
    val hp: Int,
    val atk: Int,
    val def: Int,
    val spatk: Int,
    val spdef: Int,
    val spd: Int,
    val generation: Int,
    val color: String,
    val gameAppearances: List<String>,
    val evolutionChain: List<EvolutionMember>,
    val moves: List<String>
)

data class EvolutionMember(
    val id: Int,
    val name: String,
    val imageUrl: String
)
