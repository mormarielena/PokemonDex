package com.example.pokemondex.features.infohub.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokemondex.R
import com.example.pokemondex.core.ui.components.DexCard
import com.example.pokemondex.features.infohub.viewmodel.HubViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HubDetailScreen(
    category: Category,
    onBack: () -> Unit,
    viewModel: HubViewModel = viewModel()
) {
    val items = viewModel.currentList
    val isLoading = viewModel.isLoading
    val selectedItemDetails = viewModel.selectedItemDetails
    var selectedTab by remember { mutableStateOf("Poké Balls") }

    // load data for the specified category
    LaunchedEffect(category.key, selectedTab) {
        viewModel.loadCategory(category.key, if (category.key == "items") selectedTab else null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // toolbar
        Surface(
            color = Color.White,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.back_arrow),
                            contentDescription = "Back",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Text(
                        text = category.label,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                
                // Show Tabs only for Items category
                if (category.key == "items") {
                    TabRow(
                        selectedTabIndex = if (selectedTab == "Poké Balls") 0 else 1,
                        containerColor = Color.White,
                        contentColor = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Tab(
                            selected = selectedTab == "Poké Balls",
                            onClick = { selectedTab = "Poké Balls" },
                            text = { 
                                Text(
                                    "Poké Balls", 
                                    fontWeight = if (selectedTab == "Poké Balls") FontWeight.Bold else FontWeight.Normal,
                                    color = if (selectedTab == "Poké Balls") MaterialTheme.colorScheme.primary else Color.Gray
                                ) 
                            }
                        )
                        Tab(
                            selected = selectedTab == "Consumables",
                            onClick = { selectedTab = "Consumables" },
                            text = { 
                                Text(
                                    "Consumables", 
                                    fontWeight = if (selectedTab == "Consumables") FontWeight.Bold else FontWeight.Normal,
                                    color = if (selectedTab == "Consumables") MaterialTheme.colorScheme.primary else Color.Gray
                                ) 
                            }
                        )
                    }
                }
            }
        }

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = category.color)
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items) { hubItem ->
                    DexCard(
                        title = hubItem.name,
                        subtitle = hubItem.id,
                        imageUrl = hubItem.imageUrl,
                        showAccentBar = false,
                        accentColor = category.color,
                        onClick = { viewModel.fetchItemDetails(category.key, hubItem.name) }
                    )
                }
            }
        }
    }
    
    // Bottom Sheet for Details
    if (selectedItemDetails != null) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.clearSelectedItemDetails() },
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .padding(bottom = 32.dp)
            ) {
                Text(
                    text = "Information",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = selectedItemDetails,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    lineHeight = 24.sp
                )
            }
        }
    }
}
