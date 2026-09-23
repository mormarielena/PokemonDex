package com.example.pokemondex.core.ui.components

import androidx.compose.ui.graphics.Color
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * unit test for the getTypeColor utility function.
 */
class PokemonTypeComponentsTest {

    @Test
    fun getTypeColor_returnsOrangeForFire() {
        // input we want to test - fire category
        val typeToTest = "fire"

        // call the function
        val resultColor = getTypeColor(typeToTest)

        // 3. assert
        val expectedColor = Color(0xFFFF6B35)
        assertEquals("The color for 'fire' type should be Orange", expectedColor, resultColor)
    }

    @Test
    fun getTypeColor_returnsBlueForWater() {
        val resultColor = getTypeColor("WATER")
        val expectedColor = Color(0xFF4A90E2)
        assertEquals("The color for 'WATER' type should be Blue, regardless of case", expectedColor, resultColor)
    }

    @Test
    fun getTypeColor_returnsDefaultForUnknownType() {
        val resultColor = getTypeColor("unknown_type")
        val expectedColor = Color(0xFFAAA9A5)
        assertEquals("An unknown type should return the default Gray color", expectedColor, resultColor)
    }
}
