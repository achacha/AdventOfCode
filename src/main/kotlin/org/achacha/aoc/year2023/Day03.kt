package org.achacha.aoc.year2023

/**
 * https://adventofcode.com/2023/day/3
 */
class Day03 {
    /**
     * Location of a number in each line
     */
    data class NumberLocationInLine(
        val start: Int,
        val end: Int,
    )

    private val numberRegex = Regex("(\\d+)")

    fun computePart1(input: String): Long {
        val grid = input.lines()

        val partNumbers = mutableListOf<Long>()
        grid.forEachIndexed { liney, gridLine ->
            val matches = numberRegex.findAll(gridLine)
            matches.forEachIndexed { posx, matchResult ->
                //println("Checking[$index]: range=${matchResult.range} value=${matchResult.value}")

                var isPartNumber = false

                // Check all chars around for non-digit and non-.
                // Line above
                if (liney > 0)
                    for (i in matchResult.range.start - 1..matchResult.range.endInclusive + 1)
                        isPartNumber = isPartNumber || isSymbolAtGridLocation(grid, i, liney - 1)

                // Left of
                isPartNumber = isPartNumber || isSymbolAtGridLocation(grid, matchResult.range.start - 1, liney)

                // Right of
                isPartNumber = isPartNumber || isSymbolAtGridLocation(grid, matchResult.range.last + 1, liney)

                // Line below
                if (liney < grid.size)
                    for (i in matchResult.range.start - 1..matchResult.range.endInclusive + 1)
                        isPartNumber = isPartNumber || isSymbolAtGridLocation(grid, i, liney + 1)

                if (isPartNumber) partNumbers.add(matchResult.value.toLong())
            }
            //println("Line[$liney]: $partNumbers")
        }
        return partNumbers.sum()
    }

    fun isSymbolAtGridLocation(grid: List<String>, x: Int, y: Int): Boolean {
        if (x < 0 || x >= grid[0].length || y < 0 || y >= grid.size) return false

        val c = grid[y][x]
        return !(c == '.' || c.isDigit())
    }

    fun computePart2(input: String): Long {
        return 0L
    }
}