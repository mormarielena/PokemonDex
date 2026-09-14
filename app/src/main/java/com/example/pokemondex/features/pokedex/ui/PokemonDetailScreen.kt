package com.example.pokemondex.features.pokedex.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pokemondex.core.data.model.Pokemon
import com.example.pokemondex.core.ui.components.TypeBadge
import com.example.pokemondex.core.ui.components.getTypeColor

@Composable
fun PokemonDetailScreen(pokemon: Pokemon, onBackClick: () -> Unit) {

    val mainColor = getTypeColor(pokemon.types.first())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(mainColor)
    ) {
        // backbutton
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.padding(top = 32.dp, start = 8.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack, 
                contentDescription = "Back",
                tint = Color.White
            )
        }

        // pokemon sprite
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = pokemon.imageUrl,
                contentDescription = pokemon.name,
                modifier = Modifier.size(200.dp)
            )
        }

        // pokemon info surface
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // name and id
                Text(
                    text = pokemon.name,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )
                Text(
                    text = "#${pokemon.id}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                // types
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    pokemon.types.forEach { type ->
                        TypeBadge(type = type)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // about title
                Text(
                    text = "About",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Start) // Aliniat la stânga
                )
                
                Spacer(modifier = Modifier.height(16.dp))

                // dimensions for pokemon
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    InfoItem(title = "Weight", value = pokemon.weight)
                    InfoItem(title = "Height", value = pokemon.height)
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // description
                Text(
                    text = pokemon.description,
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// reusable component
@Composable
fun InfoItem(title: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = title, fontSize = 12.sp, color = Color.Gray)
    }
}
