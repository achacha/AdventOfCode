package org.achacha.aoc.year2023

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun Array<CharArray>.asString(): String = this.joinToString("\n", transform = { it.joinToString("") })

/**
 * https://adventofcode.com/2023/day/10
 *
 * Pair: first=x, second=y
 */
class Day10(input: String) {
    // input grid
    lateinit var grid: Array<CharArray>

    // part2 generated grid with +1 outside frame
    lateinit var grid1: Array<CharArray>

    // Double-size +1 array for fill expanded
    lateinit var grid2: Array<CharArray>

    var startPoint: Pair<Int, Int>? = null

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

    fun part2(): Int {
        navigateUntilS()
        println("BEFORE (grid1)\n" + grid1.asString())
        expandGrid1ToGrid2()
        println("AFTER EXPAND\n" + grid2.asString())
        runBlocking { floodFillGrid2(Pair(0, 0)) }
        println("AFTER FILL\n" + grid2.asString())
        normalizeAfterFillGrid2()
        println("AFTER NORMALIZE\n" + grid2.asString())
        return countUnfilledSpaceGrid2()
    }

    fun parseInput(input: String) {
        if (input.isBlank()) return

        grid = input.lines()
            .map(String::trimStart)
            .map(String::trimEnd)
            .map(String::toCharArray)
            .mapIndexed { index, charArray ->
                charArray.indexOf('S').also { offset -> if (offset >= 0) startPoint = Pair(offset, index) }
                charArray
            }
            .toTypedArray()

//        println(grid.asString())

        // add 1 to start and end
        grid1 = Array(grid.size + 2) { CharArray(grid[0].size + 2) { '.' } }

        // double size of expanded and add 1 to start and end
        grid2 = Array((grid.size + 2) * 2) { CharArray((grid[0].size + 2) * 2) { '.' } }
    }

    fun findFirstClockwiseStartPoint(): Pair<Int, Int> {

        var d0000: Pair<Int, Int>? = null
        var d0300: Pair<Int, Int>? = null
        var d0600: Pair<Int, Int>? = null
        var d0900: Pair<Int, Int>? = null

        startPoint?.let { startPoint ->
            // Try 00:00
            if (startPoint.second > 0) {
                when (grid[startPoint.second - 1][startPoint.first]) {
                    '|', '7', 'F' -> {
                        d0000 = Pair(startPoint.first, startPoint.second - 1)
                    }
                }
            }

            // Try 03:00
            if (startPoint.first < grid[0].size - 1) {
                when (grid[startPoint.second][startPoint.first + 1]) {
                    '-', 'J', '7' -> {
                        d0300 = Pair(startPoint.first + 1, startPoint.second)
                    }
                }
            }

            // Try 06:00
            if (startPoint.second < grid.size - 1) {
                when (grid[startPoint.second + 1][startPoint.first]) {
                    '|', 'J', 'L' -> {
                        d0600 = Pair(startPoint.first, startPoint.second + 1)
                    }
                }
            }

            // Try 09:00
            if (startPoint.first > 0) {
                when (grid[startPoint.second][startPoint.first - 1]) {
                    '-', 'L', 'F' -> {
                        d0900 = Pair(startPoint.first - 1, startPoint.second)
                    }
                }
            }
        }


        // Determine what S is
        val realS: Char = if (d0000 != null) {
            if (d0300 != null) 'L'
            else if (d0600 != null) '|'
            else if (d0900 != null) 'J'
            else throw IllegalStateException("d0000 missing second")
        } else if (d0300 != null) {
            if (d0600 != null) 'F'
            else if (d0900 != null) '-'
            else throw IllegalStateException("d0300 missing second")
        } else if (d0600 != null) {
            if (d0900 != null) '7'
            else throw IllegalStateException("d0600 missing second")
        } else throw IllegalStateException("Missing 2 exits")

        // Set start's actual char
        if (startPoint == null) throw IllegalStateException("No starting point found")
        else grid1[startPoint!!.second + 1][startPoint!!.first + 1] = realS

        // Return first found
        return d0000 ?: d0300 ?: d0600 ?: d0900 ?: throw IllegalStateException("NO valid starting path found")
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
        startPoint?.let { startPoint ->
            var previous = startPoint
//            println("Start at $previous")

            var current = findFirstClockwiseStartPoint()
            var direction = getDirection(current, previous)
//            println("Moved $direction to $current=${grid[current.second][current.first]}")
            grid1[current.second + 1][current.first + 1] = grid[current.second][current.first]

            while (grid[current.second][current.first] != 'S') {
                ++steps
                previous = current
                current = next(current, direction)
                direction = getDirection(current, previous)
//                println("Moved $direction to $current=${grid[current.second][current.first]}")

                // Map the path onto grid1 ignoring S
                if (grid[current.second][current.first] != 'S')
                    grid1[current.second + 1][current.first + 1] = grid[current.second][current.first]

            }
            //println("Found S in $steps steps")
        } ?: throw IllegalStateException("Start point not found")

        return steps
    }

    /**
     * Flood fill grid1
     */
    fun floodFill() {
        runBlocking {
            launch {
                fillGrid1Aggressive(Pair(0, 0), Direction.S)
                fillGrid1Aggressive(Pair(0, 0), Direction.E)
            }
        }
    }

    /**
     * Expects navigate to populate grid1
     */
    fun expandGrid1ToGrid2() {
        // Use grid to populate grid2 doublesize
        for (y in grid1.indices)
            for (x in grid1[0].indices) {
                when (grid1[y][x]) {
                    'F' -> {
                        grid2[y * 2][x * 2] = 'F'
                        grid2[y * 2][x * 2 + 1] = '-'
                        grid2[y * 2 + 1][x * 2] = '|'
                    }

                    '7' -> {
                        grid2[y * 2][x * 2] = '7'
                        grid2[y * 2][x * 2 + 1] = '.'
                        grid2[y * 2 + 1][x * 2] = '|'
                    }

                    'L' -> {
                        grid2[y * 2][x * 2] = 'L'
                        grid2[y * 2][x * 2 + 1] = '-'
                        grid2[y * 2 + 1][x * 2] = '.'
                    }

                    'J' -> {
                        grid2[y * 2][x * 2] = 'J'
                        grid2[y * 2][x * 2 + 1] = '.'
                        grid2[y * 2 + 1][x * 2] = '.'
                    }

                    '-' -> {
                        grid2[y * 2][x * 2] = '-'
                        grid2[y * 2][x * 2 + 1] = '-'
                        grid2[y * 2 + 1][x * 2] = '.'
                    }

                    '|' -> {
                        grid2[y * 2][x * 2] = '|'
                        grid2[y * 2][x * 2 + 1] = '.'
                        grid2[y * 2 + 1][x * 2] = '|'
                    }
                }
            }
    }

    /**
     * Fills grid1
     *
     * '.' is an empty cell, '~' is a filled cell
     */
    suspend fun floodFillGrid2(point: Pair<Int, Int>): Unit = coroutineScope {
        if (
            point.second < grid2.size
            && point.second >= 0
            && point.first < grid2[0].size
            && point.first >= 0
        ) {
            val current = grid2[point.second][point.first]

            // fill if empty
            if (current == '.') {
                grid2[point.second][point.first] = '~'
                launch { floodFillGrid2(Pair(point.first, point.second - 1)) }
                launch { floodFillGrid2(Pair(point.first, point.second + 1)) }
                launch { floodFillGrid2(Pair(point.first - 1, point.second)) }
                launch { floodFillGrid2(Pair(point.first + 1, point.second)) }
            }
        }
    }

    fun normalizeAfterFillGrid2() {
        for (y in grid2.indices)
            for (x in grid2[0].indices step 2) {
                if (
                    (grid2[y][x] == '.' && grid2[y][x + 1] == '.')
                    || (grid2[y][x] == '|' && grid2[y][x + 1] == '.')
                    || (grid2[y][x] == 'J' && grid2[y][x + 1] == '.')
                    || (grid2[y][x] == '7' && grid2[y][x + 1] == '.')
                )
                    grid2[y][x + 1] = '*'
            }

        // Eliminate 2nd rows as they are synthetic
        for (y in grid2.indices step 2)
            for (x in grid2[0].indices) {
                grid2[y + 1][x] = '*'
            }

    }

    suspend fun fillGrid1Aggressive(point: Pair<Int, Int>, direction: Direction): Unit = coroutineScope {
        if (
            point.second < grid1.size
            && point.second >= 0
            && point.first < grid1[0].size
            && point.first >= 0
        ) {
            val current = grid1[point.second][point.first]
            println("FILL: `$current` at ($point) in $direction")
            if (current != '~') {
                if (current == '.') {
                    // Current point is empty, go in all directions
                    grid1[point.second][point.first] = '~'
                    fillGrid1Aggressive(Pair(point.first, point.second - 1), Direction.N)
                    fillGrid1Aggressive(Pair(point.first, point.second + 1), Direction.S)
                    fillGrid1Aggressive(Pair(point.first - 1, point.second), Direction.W)
                    fillGrid1Aggressive(Pair(point.first + 1, point.second), Direction.E)
                } else when (direction) {
                    Direction.W -> {
                        // | will result in N and S move from here
                        if (current == '|')
                            launch {
                                fillGrid1Aggressive(Pair(point.first, point.second - 1), Direction.N)
                                fillGrid1Aggressive(Pair(point.first, point.second + 1), Direction.S)
                            }

                        // F L 7 J will continue W
                        if (current == 'F' || current == 'L' || current == '7' || current == 'J')
                            launch {
                                fillGrid1Aggressive(Pair(point.first + 1, point.second), Direction.W)
                            }
                    }

                    Direction.E -> {
                        // | will result in N and S move from here
                        if (current == '|')
                            launch {
                                fillGrid1Aggressive(Pair(point.first, point.second - 1), Direction.N)
                                fillGrid1Aggressive(Pair(point.first, point.second + 1), Direction.S)
                            }

                        // J 7 F L will continue E
                        if (current == 'J' || current == '7' || current == 'F' || current == 'L')
                            launch {
                                fillGrid1Aggressive(Pair(point.first - 1, point.second), Direction.E)
                            }
                    }

                    Direction.N -> {
                        // J L 7 F will continue N
                        if (current == 'J' || current == 'L' || current == '7' || current == 'F')
                            launch {
                                fillGrid1Aggressive(Pair(point.first, point.second - 1), Direction.N)
                            }

                        // - will result in E and W move from here
                        if (current == '-')
                            launch {
                                fillGrid1Aggressive(Pair(point.first - 1, point.second), Direction.W)
                                fillGrid1Aggressive(Pair(point.first + 1, point.second), Direction.E)
                            }
                    }

                    Direction.S -> {
                        // - will result in E and W move from here
                        if (current == '-')
                            launch {
                                fillGrid1Aggressive(Pair(point.first - 1, point.second), Direction.W)
                                fillGrid1Aggressive(Pair(point.first + 1, point.second), Direction.E)
                            }

                        // J L 7 F will continue S
                        if (current == 'J' || current == 'L' || current == '7' || current == 'F')
                            launch {
                                fillGrid1Aggressive(Pair(point.first, point.second + 1), Direction.S)
                            }
                    }
                }
            }
        }
    }

    /**
     * Count unfilled space in grid1
     */
    fun countUnfilledSpaceGrid1() = grid1.sumOf { it.count { c -> c == '.' } }

    /**
     * Count unfilled space in grid2 and divide by 2 (due to expand)
     */
    fun countUnfilledSpaceGrid2() = grid2.sumOf { it.count { c -> c == '.' } }
}