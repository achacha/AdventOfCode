package org.achacha.aoc.year2023

/**
 * https://adventofcode.com/2023/day/2
 */
class Day02 {
    data class Game(
        val id: Int,
        val hands: List<Hand>
    )

    data class Hand(
        val red: Int,
        val green: Int,
        val blue: Int,
    )

    /**
     * INPUT: Game data to be parsed
     * OUTPUT: Sum of game #s that fits the criteria
     * CRITERIA: 12 red cubes, 13 green cubes, and 14 blue cubes
     */
    fun compute(input: String): Int {
        var gameData: List<Game> = parseInput(input)

        val possibleGames: List<Game> = processGames(gameData)

        return possibleGames.map(Game::id).sum()
    }

    private fun processGames(gameData: List<Game>): List<Game> {
        // TODO

        return listOf()
    }

    private fun parseInput(input: String): List<Game> {
        // TODO

        return listOf()
    }
}