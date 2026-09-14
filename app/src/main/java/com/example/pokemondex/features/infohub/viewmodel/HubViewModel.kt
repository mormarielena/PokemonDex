package com.example.pokemondex.features.infohub.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemondex.core.data.network.PokeApiService
import kotlinx.coroutines.launch

//data model for dashboard
data class HubStats(
    val berryCount: String = "...",
    val itemCount: String = "...",
    val locationCount: String = "..."
)


data class HubItem(
    val name: String,
    val id: String,
    val imageUrl: String
)

class HubViewModel : ViewModel() {

    var stats by mutableStateOf(HubStats())
        private set

    var currentList by mutableStateOf<List<HubItem>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    init {
        loadStats()
    }

    private fun loadStats() {
        viewModelScope.launch {
            try {
                val berries = PokeApiService.instance.getBerryList(limit = 1)
                val items = PokeApiService.instance.getItemList(limit = 1)
                val locations = PokeApiService.instance.getLocationList(limit = 1)

                stats = HubStats(
                    berryCount = "${berries.count} types",
                    itemCount = "${items.count} items",
                    locationCount = "${locations.count} places"
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadCategory(key: String) {
        viewModelScope.launch {
            isLoading = true
            currentList = emptyList()
            try {
                val response = when (key) {
                    "berries" -> PokeApiService.instance.getBerryList(limit = 30)
                    "items" -> PokeApiService.instance.getItemList(limit = 30)
                    else -> PokeApiService.instance.getLocationList(limit = 30)
                }

                currentList = response.results.map { entry ->
                    //  extract id from url
                    val id = entry.url.split("/").dropLast(1).last()

                    val imageUrl = if (key == "locations") {
                        ""
                    } else if (key == "berries") {
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/${entry.name}-berry.png"
                    } else {
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/${entry.name}.png"
                    }

                    HubItem(
                        name = entry.name.replace("-", " ").replaceFirstChar { it.uppercase() },
                        id = "#$id",
                        imageUrl = imageUrl
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }
}
