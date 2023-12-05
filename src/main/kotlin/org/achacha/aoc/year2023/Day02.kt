package org.achacha.aoc.year2023

/**
 * https://adventofcode.com/2023/day/2
 */
class Day02(
    private val redLimit: Int,
    private val greenLimit: Int,
    private val blueLimit: Int,
) {
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
        var red = 0
        var green = 0
        var blue = 0
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
        return games.filter { game ->
            game.hands.all { hand ->
                hand.red <= redLimit && hand.green <= greenLimit && hand.blue <= blueLimit
            }
        }
    }


}