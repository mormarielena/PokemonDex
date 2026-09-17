package com.example.pokemondex.core.data.network.model

import com.google.gson.annotations.SerializedName

//list responde
data class PokemonListResponse(
    val count: Int,
    val results: List<PokemonEntryDto>
)

//element from list
data class PokemonEntryDto(
    val name: String,
    val url: String
)

//pokemon detalis
data class PokemonDetailDto(
    val id: Int,
    val name: String,
    val weight: Int,
    val height: Int,
    val types: List<TypeSlotDto>,
    val stats: List<StatSlotDto>,
    val abilities: List<AbilitySlotDto>,
    val moves: List<MoveSlotDto>,
    @SerializedName("game_indices")
    val gameIndices: List<GameIndexDto>,
    val sprites: SpritesDto
)

data class StatSlotDto(
    @SerializedName("base_stat")
    val baseStat: Int,
    val stat: StatDto
)

data class StatDto(
    val name: String
)

data class AbilitySlotDto(
    val ability: AbilityDto
)

data class AbilityDto(
    val name: String
)

data class MoveSlotDto(
    val move: NamedResourceDto
)

data class GameIndexDto(
    val version: NamedResourceDto
)

data class NamedResourceDto(
    val name: String,
    val url: String
)

//pokemon species
data class PokemonSpeciesDto(
    @SerializedName("flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntryDto>,
    val genera: List<GenusDto>,
    val color: NamedResourceDto,
    val generation: NamedResourceDto,
    @SerializedName("evolution_chain")
    val evolutionChain: EvolutionChainUrlDto
)

data class EvolutionChainUrlDto(
    val url: String
)

data class GenusDto(
    val genus: String,
    val language: LanguageDto
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

//evolution chain response
data class EvolutionChainDto(
    val chain: ChainLinkDto
)

data class ChainMemberDto(
    val id: Int,
    val name: String,
    val imageUrl: String
)

data class ChainLinkDto(
    @SerializedName("evolves_to")
    val evolvesTo: List<ChainLinkDto>,
    val species: NamedResourceDto
)

//infohub details
data class NamedResourceDetailDto(
    val id: Int,
    val name: String,
    val sprites: ItemSpritesDto? = null
)

data class ItemSpritesDto(
    @SerializedName("default")
    val default: String
)
