package com.example.pokemondex.core.data.model

data class Pokemon(
    val id: String,
    val name: String,
    val imageUrl: String,
    val types: List<String>,
    val weight: String,
    val height: String,
    val description: String
)
