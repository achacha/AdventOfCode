package org.achacha.aoc.year2023

import kotlin.math.max
import kotlin.math.min

/**
 * https://adventofcode.com/2023/day/3
 */
class Day03 {
    private val numberRegex = Regex("(\\d+)")
    private val gearRegex = Regex("([*])")

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

    fun isDigitAtGridLocation(grid: List<String>, x: Int, y: Int): Boolean {
        if (x < 0 || x >= grid[0].length || y < 0 || y >= grid.size) return false

        val c = grid[y][x]
        return c.isDigit()
    }

    fun computePart2(input: String): Long {
        var sumOfProducts = 0L
        val grid = input.lines()
        grid.forEachIndexed { y, line ->
            // Find *
            gearRegex.findAll(line).forEachIndexed { x, matchResult ->
                val parts = findNumbersAroundGear(grid, matchResult.range.first, y)
                //println("x=${matchResult.range.first} y=$y nums=${parts}")

                // If 2 numbers found add product of numbers
                if (parts.size == 2)
                    sumOfProducts += parts[0] * parts[1]
            }
        }

        return sumOfProducts
    }

    fun findNumbersAroundGear(grid: List<String>, x: Int, y: Int): List<Long> {
        // Sanity check just in case
        if (grid[y][x] != '*') throw IllegalArgumentException("location ($x, $y) is not *, value=`${grid[y][x]}`")

        /*
        case when 2 numbers can exist in line above (or below)
      111 111
         *


        111*111 - left or right case

        11111  - 1 above, go left and right to get the number
         *

         */
        val numbers = mutableListOf<Long>()

        // check top
        if (y > 0) {
            val numAbove = getNumberAtOffset(grid[y-1], x)
            if (numAbove != null) numbers.add(numAbove)
            else {
                // directly above is not a number, check diagonals above
                numbers.addIfNotNull(getNumberAtOffset(grid[y-1], x-1))
                numbers.addIfNotNull(getNumberAtOffset(grid[y-1], x+1))
            }
        }

        // check left
        if (x - 1 >= 0)
            numbers.addIfNotNull(getNumberAtOffset(grid[y], x-1))

        // check right
        if (x + 1 < grid[0].length)
            numbers.addIfNotNull(getNumberAtOffset(grid[y], x+1))

        // check bottom
        if (y < grid.size-1) {
            val numBelow = getNumberAtOffset(grid[y+1], x)
            if (numBelow != null) numbers.add(numBelow)
            else {
                // directly above is not a number, check diagonals above
                numbers.addIfNotNull(getNumberAtOffset(grid[y+1], x-1))
                numbers.addIfNotNull(getNumberAtOffset(grid[y+1], x+1))
            }
        }

        return numbers
    }

    fun getNumberAtOffset(line: String, x: Int): Long? {
        if (x < 0 || x >= line.length || !line[x].isDigit()) return null

        var start = x
        var end = x
        while (start >= 0) {
            if (start-1 < 0) break
            if (line[start-1].isDigit()) start-- else break
        }
        while (end < line.length) {
            if (end+1 >= line.length) break
            if (line[end+1].isDigit()) end++ else break
        }

        return line.substring(start..end).toLong()
    }

    fun getNumberAtOffset2(line: String, x: Int): Long? {
        if (x < 0 || x >= line.length || !line[x].isDigit()) return null

        var start = x
        var end = x
        while (start > 0 && line[start].isDigit()) start--
        while (end < line.length && line[end].isDigit()) end++

        return line.substring(max(0, start)..min(end-1, line.length-1)).toLong()
    }
}

fun <E> MutableList<E>.addIfNotNull(e: E?) {
    e?.let { this.add(e) }
}
