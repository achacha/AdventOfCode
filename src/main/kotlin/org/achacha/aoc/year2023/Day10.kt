package org.achacha.aoc.year2023


fun Array<CharArray>.asString(): String = this.joinToString("\n", transform = { it.joinToString("") })

/**
 * https://adventofcode.com/2023/day/10
 *
 * Pair: first=x, second=y
 */
class Day10(input: String) {
    lateinit var grid: Array<CharArray>

    lateinit var startPoint: Pair<Int, Int>

    /*
    | is a vertical pipe connecting north and south.
    - is a horizontal pipe connecting east and west.
    L is a 90-degree bend connecting north and east.
    J is a 90-degree bend connecting north and west.
    7 is a 90-degree bend connecting south and west.
    F is a 90-degree bend connecting south and east.
    . is ground; there is no pipe in this tile.
    S is the starting position of the animal; there is a pipe on this tile, but your sketch doesn't show what shape the pipe has.
     */
    val PIPES = "|-LJ7F"

    init {
        parseInput(input)
    }

    fun part1(): Int {
        return navigateUntilS() / 2
    }

    fun parseInput(input: String) {
        grid = input.lines()
            .map(String::toCharArray)
            .mapIndexed { index, charArray ->
                charArray.indexOf('S').also { offset -> if (offset >= 0) startPoint = Pair(offset, index) }
                charArray
            }
            .toTypedArray()
//        println(grid.asString())
    }

    fun findFirstClockwiseStartPoint(): Pair<Int, Int> {

        // Try 00:00
        if (startPoint.second > 0) {
            when (grid[startPoint.second - 1][startPoint.first]) {
                '|', '7', 'F' -> return Pair(startPoint.first, startPoint.second - 1)
            }
        }

        // Try 03:00
        if (startPoint.first < grid[0].size - 1) {
            when (grid[startPoint.second][startPoint.first + 1]) {
                '-', 'J', '7' -> return Pair(startPoint.first + 1, startPoint.second)
            }
        }

        // Try 06:00
        if (startPoint.second < grid.size - 1) {
            when (grid[startPoint.second + 1][startPoint.first]) {
                '|', 'J', 'L' -> return Pair(startPoint.first + 1, startPoint.second)
            }
        }

        // Try 09:00
        if (startPoint.first > 0) {
            when (grid[startPoint.second][startPoint.first - 1]) {
                '-', 'L', 'F' -> return Pair(startPoint.first - 1, startPoint.second)
            }
        }

        throw IllegalStateException("Starting point has no exit: $startPoint")
    }

    enum class Direction { N, E, W, S }

    fun getDirection(current: Pair<Int, Int>, previous: Pair<Int, Int>): Direction {
        return if (current.second > previous.second) Direction.S
        else if (current.second < previous.second) Direction.N
        else if (current.first > previous.first) Direction.E
        else Direction.W
    }

    /**
     * Given direction traveling and current point determine next point
     */
    fun next(current: Pair<Int, Int>, direction: Direction): Pair<Int, Int> {
        val pipe = grid[current.second][current.first]
        when (pipe) {
            '|' -> {
                return if (direction == Direction.N) {
                    Pair(current.first, current.second - 1)
                } else {
                    Pair(current.first, current.second + 1)
                }
            }

            '-' -> {
                return if (direction == Direction.W) {
                    Pair(current.first - 1, current.second)
                } else {
                    Pair(current.first + 1, current.second)
                }
            }

            'L' -> {
                return if (direction == Direction.W) {
                    Pair(current.first, current.second - 1)
                } else {
                    Pair(current.first + 1, current.second)
                }
            }

            'J' -> {
                return if (direction == Direction.E) {
                    Pair(current.first, current.second - 1)
                } else {
                    Pair(current.first - 1, current.second)
                }
            }

            'F' -> {
                return if (direction == Direction.W) {
                    Pair(current.first, current.second + 1)
                } else {
                    Pair(current.first + 1, current.second)
                }
            }

            '7' -> {
                return if (direction == Direction.E) {
                    Pair(current.first, current.second + 1)
                } else {
                    Pair(current.first - 1, current.second)
                }
            }

            else -> throw IllegalStateException("Invalid point at $current going $direction")
        }
    }

    fun navigateUntilS(): Int {
        var steps = 1
        var previous = startPoint
        //println("Start at $previous")
        var current = findFirstClockwiseStartPoint()
        var direction = getDirection(current, previous)
        //println("Moved $direction to $current=${grid[current.second][current.first]}")
        while (grid[current.second][current.first] != 'S') {
            ++steps
            previous = current
            current = next(current, direction)
            direction = getDirection(current, previous)
            //println("Moved $direction to $current=${grid[current.second][current.first]}")
        }
        //println("Found S in $steps steps")

        return steps
    }
}