package com.example.pokemondex.features.teammaker.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * unit test for the TeamMakerViewModel math logic.
 */
class TeamMakerViewModelTest {

    // variable for viewmodel
    private lateinit var viewModel: TeamMakerViewModel

    // create empty viewmodel for each test run
    @Before
    fun setup() {
        viewModel = TeamMakerViewModel()
    }

    @Test
    fun teamHp_isZeroWhenTeamIsEmpty() {
        // assert - a new viewmodel has 0 value
        assertEquals(0, viewModel.teamHp)
        assertEquals(0, viewModel.teamAttack)
        assertEquals(0, viewModel.teamDefense)
    }

    @Test
    fun teamHp_calculatesCorrectAverageForTwoPokemon() {
        // get two predefined pokemons to test on
        val pikachu = viewModel.availablePokemon.find { it.name == "Pikachu" }!!         // Pikachu HP = 35
        val snorlax = viewModel.availablePokemon.find { it.name == "Snorlax" }!!        // Snorlax HP = 160

        // add them to the first 2 slots
        viewModel.addPokemonToSlot(0, pikachu)
        viewModel.addPokemonToSlot(1, snorlax)

        // assert - calculate expected average
        val expectedAverageHp = 97
        
        assertEquals(
            "The average HP of Pikachu(35) and Snorlax(160) should be 97",
            expectedAverageHp,
            viewModel.teamHp
        )
    }

    @Test
    fun filledCount_increasesWhenPokemonAdded() {
        // get a pokemon
        val charizard = viewModel.availablePokemon.find { it.name == "Charizard" }!!

        // add it on slot 3
        viewModel.addPokemonToSlot(3, charizard)

        // assert - the count should be 1
        assertEquals(1, viewModel.filledCount)
    }
}
