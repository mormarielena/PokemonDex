package com.example.pokemondex.features.infohub.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemondex.core.data.network.PokeApiService
import kotlinx.coroutines.launch

data class HubStats(
    val berryCount: String = "Loading...",
    val itemCount: String = "Loading...",
    val locationCount: String = "Loading..."
)

data class HubItem(
    val id: String,
    val name: String,
    val imageUrl: String
)

class HubViewModel(application: Application) : AndroidViewModel(application) {

    var stats by mutableStateOf(HubStats())
        private set
        
    var currentList by mutableStateOf<List<HubItem>>(emptyList())
        private set
        
    var isLoading by mutableStateOf(false)
        private set
        
    var selectedItemDetails by mutableStateOf<String?>(null)
        private set

    init {
        fetchStats()
    }

    private fun fetchStats() {
        viewModelScope.launch {
            try {
                val berries = PokeApiService.instance.getBerryList(limit = 1).count
                val items = PokeApiService.instance.getItemList(limit = 1).count
                val locations = PokeApiService.instance.getLocationList(limit = 1).count
                
                stats = HubStats(
                    berryCount = "$berries available",
                    itemCount = "$items items",
                    locationCount = "$locations mapped"
                )
            } catch (_: Exception) {
                stats = HubStats("Error", "Error", "Error")
            }
        }
    }

    fun loadCategory(key: String, subCategory: String? = null) {
        viewModelScope.launch {
            isLoading = true
            try {
                // If it's the "items" category and a subCategory is provided, we fetch from the item-category endpoint.
                val results = when(key) {
                    "berries" -> PokeApiService.instance.getBerryList(limit = 20).results
                    "items" -> {
                        when (subCategory) {
                            "Poké Balls" -> PokeApiService.instance.getItemCategory("standard-balls").items
                            "Consumables" -> PokeApiService.instance.getItemCategory("healing").items
                            else -> PokeApiService.instance.getItemList(limit = 20).results // fallback
                        }
                    }
                    "places" -> PokeApiService.instance.getLocationList(limit = 20).results
                    else -> emptyList()
                }

                currentList = results.map { resource ->
                    val urlParts = resource.url.split("/").filter { it.isNotEmpty() }
                    val id = urlParts.last()
                    
                    val imgUrl = when(key) {
                        "berries" -> "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/${resource.name}-berry.png"
                        "items" -> "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/${resource.name}.png"
                        else -> "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/town-map.png"
                    }
                    
                    HubItem(
                        id = "#${id.padStart(3, '0')}",
                        name = resource.name.replace("-", " ").replaceFirstChar { it.uppercase() },
                        imageUrl = imgUrl
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    fun fetchItemDetails(categoryKey: String, rawName: String) {
        viewModelScope.launch {
            try {
                // Determine actual api name format (lowercase, replace spaces with hyphens)
                val apiName = rawName.lowercase().replace(" ", "-")

                val detailString = when (categoryKey) {
                    "items" -> {
                        val item = PokeApiService.instance.getItemDetail(apiName)
                        val effect = item.effectEntries.find { it.language.name == "en" }?.shortEffect
                            ?: "No effect description available."
                        "Effect: ${effect.replace("\n", " ")}"
                    }
                    "berries" -> {
                        // berry api requires the berry name without the word "berry"
                        val realName = apiName.replace("-berry", "")
                        val berry = PokeApiService.instance.getBerryDetail(realName)
                        "Size: ${berry.size} mm\nFirmness: ${berry.firmness.name.replace("-", " ").replaceFirstChar { it.uppercase() }}"
                    }
                    "places" -> {
                        val location = PokeApiService.instance.getLocationDetail(apiName)
                        val regionName = location.region?.name?.replaceFirstChar { it.uppercase() } ?: "Unknown Region"
                        "Region: $regionName"
                    }
                    else -> "No details available."
                }
                
                selectedItemDetails = detailString
            } catch (e: Exception) {
                e.printStackTrace()
                selectedItemDetails = "Could not fetch details."
            }
        }
    }

    fun clearSelectedItemDetails() {
        selectedItemDetails = null
    }
}
