package com.example.pokemondex.core.data.network

import com.example.pokemondex.core.data.network.model.EvolutionChainDto
import com.example.pokemondex.core.data.network.model.PokemonDetailDto
import com.example.pokemondex.core.data.network.model.PokemonListResponse
import com.example.pokemondex.core.data.network.model.PokemonSpeciesDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * api calls for pokemon
 */
interface PokeApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): PokemonListResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonDetails(
        @Path("name") name: String
    ): PokemonDetailDto

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(
        @Path("id") id: Int
    ): PokemonSpeciesDto

    @GET
    suspend fun getEvolutionChain(
        @Url url: String
    ): EvolutionChainDto

    // endpoints infohub
    
    @GET("berry")
    suspend fun getBerryList(
        @Query("limit") limit: Int = 20
    ): PokemonListResponse

    @GET("item")
    suspend fun getItemList(
        @Query("limit") limit: Int = 20
    ): PokemonListResponse

    @GET("location")
    suspend fun getLocationList(
        @Query("limit") limit: Int = 20
    ): PokemonListResponse

    //retrofit companion object
    companion object {
        private const val BASE_URL = "https://pokeapi.co/api/v2/"

        val instance: PokeApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PokeApiService::class.java)
        }
    }
}
