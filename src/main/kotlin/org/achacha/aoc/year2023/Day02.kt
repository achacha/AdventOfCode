package org.achacha.aoc.year2023

import jdk.jshell.spi.ExecutionControl.RunException
import java.lang.RuntimeException

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
        val gameData: List<Game> = parseInput(input)

        val possibleGames: List<Game> = processGames(gameData)

        return possibleGames.map(Game::id).sum()
    }

    private fun parseInput(input: String): List<Game> =
        input.reader().readLines()
            .map {
                println("GAME: $it")

                val gameSplit = it.lowercase().split(":")
                if (gameSplit.size < 2) throw RuntimeException("Inavlid game line, missing `:` in `$it`")

                // Get game ID
                if (!gameSplit[0].startsWith("game ")) throw RuntimeException("Inavlid game id, noty starting with `game ` in `${gameSplit[0]}`")
                val gameId = gameSplit[0].substring(5).toInt()

                // Extract sizes

                Game(gameId, listOf())
            }

    private fun processGames(gameData: List<Game>): List<Game> {
        // TODO

        return listOf()
    }
}