package com.example.pokemondex.features.infohub.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

/**
 * UI Test for the HubDetailScreen
 */
class HubDetailScreenTest {

    // compose rule for ui test
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun itemsCategory_displaysPokeBallsAndConsumablesTabs() {
        // create a mock Items category
        val itemsCategory = Category(
            key = "items", 
            label = "Items", 
            icon = Icons.Default.Build, 
            color = Color.Blue, 
            bg = Color.LightGray
        )

        // render screen with the mock category
        composeTestRule.setContent {
            HubDetailScreen(
                category = itemsCategory,
                onBack = {}
            )
        }

        // assert - verify both tabs are displayed
        val pokeBallsTab = composeTestRule.onNodeWithText("Poké Balls")
        val consumablesTab = composeTestRule.onNodeWithText("Consumables")

        pokeBallsTab.assertIsDisplayed()
        consumablesTab.assertIsDisplayed()
        
        // pokeballs should be default
        pokeBallsTab.assertIsSelected()

        // move to the consumables tab
        consumablesTab.performClick()

        // assert - consumables should be the selected tab
        consumablesTab.assertIsSelected()
    }
}
