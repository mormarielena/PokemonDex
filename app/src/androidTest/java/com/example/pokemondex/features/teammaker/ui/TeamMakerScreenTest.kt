package com.example.pokemondex.features.teammaker.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

/**
 * UI Test for the Team Maker screen
 */
class TeamMakerScreenTest {

    // rule for compose ui environment
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun clickingEmptySlot_opensSelectionBottomSheet() {
        // render teammaker
        composeTestRule.setContent {
            TeamMakerScreen()
        }

        // verifiy the boottom sheet is not visible yet
        composeTestRule.onNodeWithText("Choose a Pokémon").assertDoesNotExist()

        // find the first empty slot and click it
        composeTestRule.onAllNodesWithText("Add")[0].performClick()

        // assert - verify the bottom sheet is open by checking if the title is visible
        composeTestRule.onNodeWithText("Choose a Pokémon").assertIsDisplayed()
    }
}
