package org.achacha.aoc.year2023

import kotlin.math.max

/**
 * https://adventofcode.com/2023/day/2
 */
class Day02(
    private val redLimit: Long,
    private val greenLimit: Long,
    private val blueLimit: Long,
) {
    data class Game(
        val id: Long,
        val hands: List<Hand>,
        var redMax: Long = 0,
        var greenMax: Long = 0,
        var blueMax: Long = 0,
    )

    data class Hand(
        val red: Long,
        val green: Long,
        val blue: Long,
    )

    /**
     * INPUT: Game data to be parsed
     * OUTPUT: Sum of game #s that fits the criteria
     * CRITERIA: 12 red cubes, 13 green cubes, and 14 blue cubes
     */
    fun computePart1(input: String): Long {
        val gameData: List<Game> = parseInput(input)

        val possibleGames: List<Game> = processGames(gameData)

        return possibleGames.map(Game::id).sum()
    }

    fun computePart2(input: String): Long {
        val gameData: List<Game> = parseInput(input)

        processGames(gameData)

        return gameData.sumOf {
            it.redMax * it.greenMax * it.blueMax
        }
    }

    private fun parseInput(input: String): List<Game> =
        input.reader().readLines()
            .map {
                println("GAME: $it")

                val gameSplit = it.lowercase().split(":")
                if (gameSplit.size < 2) throw RuntimeException("Inavlid game line, missing `:` in `$it`")

                // Get game ID
                if (!gameSplit[0].startsWith("game ")) throw RuntimeException("Inavlid game id, noty starting with `game ` in `${gameSplit[0]}`")
                val gameId = gameSplit[0].substring(5).toLong()

                // Extract sizes
                val handSplit = gameSplit[1].split(";")
                val hands = handSplit
                    .map {
                        it.split(",").map { it.trimIndent() }
                    }
                    .map {
                        parseHand(it)
                    }
                    .toList()

                Game(gameId, hands)
            }

    /**
     * Hand is [3 green, 4 blue, 1 red]
     */
    fun parseHand(input: List<String>): Hand {
        var quantity = 0
        var red = 0L
        var green = 0L
        var blue = 0L
        input.forEach {
            val splitCube = it.split(" ")
            quantity = splitCube[0].toInt()
            when (splitCube[1].trimIndent()) {
                "red" -> red += quantity
                "green" -> green += quantity
                "blue" -> blue += quantity
                else -> throw RuntimeException("Invalid cube: $splitCube")
            }
        }
        return Hand(red, green, blue)
    }

    /**
     * Filter out any game where a hand doesn't meet the limit
     */
    fun processGames(games: List<Game>): List<Game> {
        games.forEach { game->
            game.hands.forEach {
                game.redMax = max(game.redMax, it.red)
                game.greenMax = max(game.greenMax, it.green)
                game.blueMax = max(game.blueMax, it.blue)
            }
        }

        return games.filter { game ->
            game.hands.all { hand ->
                hand.red <= redLimit && hand.green <= greenLimit && hand.blue <= blueLimit
            }
        }
    }
}