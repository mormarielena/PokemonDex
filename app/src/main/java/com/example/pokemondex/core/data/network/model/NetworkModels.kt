package com.example.pokemondex.core.data.network.model

import com.google.gson.annotations.SerializedName

/**
 * list response
 */
data class PokemonListResponse(
    val count: Int,
    val results: List<PokemonEntryDto>
)

/**
 * element from list
 */
data class PokemonEntryDto(
    val name: String,
    val url: String
)

/**
 * pokemon details
 */
data class PokemonDetailDto(
    val id: Int,
    val name: String,
    val weight: Int,
    val height: Int,
    val types: List<TypeSlotDto>,
    val sprites: SpritesDto
)

/**
 * pokemon type response
 */
data class PokemonSpeciesDto(
    @SerializedName("flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntryDto>
)

data class FlavorTextEntryDto(
    @SerializedName("flavor_text")
    val flavorText: String,
    val language: LanguageDto
)

data class LanguageDto(
    val name: String
)

data class TypeSlotDto(
    val type: TypeDto
)

data class TypeDto(
    val name: String
)

data class SpritesDto(
    val other: OtherSpritesDto
)

data class OtherSpritesDto(
    @SerializedName("official-artwork")
    val officialArtwork: ArtworkDto
)

data class ArtworkDto(
    @SerializedName("front_default")
    val frontDefault: String
)

/**
 * infohub details
 */
data class NamedResourceDetailDto(
    val id: Int,
    val name: String,
    val sprites: ItemSpritesDto? = null
)

data class ItemSpritesDto(
    @SerializedName("default")
    val default: String
)
