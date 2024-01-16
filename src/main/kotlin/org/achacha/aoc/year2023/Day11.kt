package org.achacha.aoc.year2023

import kotlin.math.abs

/**
 * https://adventofcode.com/2023/day/11
 */
class Day11(
    val input: String
) {
    lateinit var inputGrid: Array<CharArray>

    var expandedGrid: Array<CharArray>

    var galaxiesExpanded = mutableListOf<Pair<Int, Int>>()

    val blankRows: MutableList<Int> = mutableListOf()
    val blankCols: MutableList<Int> = mutableListOf()

    init {
        parseInput(input)

        // Allocate expanded grid
        expandedGrid = Array(inputGrid.size + blankRows.size) { CharArray(inputGrid[0].size + blankCols.size) { '.' } }

        mapGalaxiesIntoExpandedGrid()
    }

    fun parseInput(input: String) {
        if (input.isBlank()) return

        inputGrid = input.lines()
            .map(String::trimStart)
            .map(String::trimEnd)
            .map(String::toCharArray)
            .toTypedArray()

        // Find blank rows
        for (y in inputGrid.indices) {
            if (inputGrid[y].all { c -> c == '.' })
                blankRows.add(y)
        }

        // Find blank columns
        outer@ for (x in inputGrid[0].indices) {
            for (y in inputGrid.indices) {
                if (inputGrid[y][x] != '.')
                    continue@outer
            }
            blankCols.add(x)
        }
    }

    fun mapGalaxiesIntoExpandedGrid() {
        var offsetY = 0
        for (y in inputGrid.indices) {
            var offsetX = 0
            if (blankRows.contains(y)) ++offsetY
            for (x in inputGrid.indices) {
                if (blankCols.contains(x)) ++offsetX
                if (inputGrid[y][x] == '#') {
                    expandedGrid[y + offsetY][x + offsetX] = '#'
                    galaxiesExpanded.add(Pair(x + offsetX, y + offsetY))
                }
            }
        }
    }

    fun distanceBetweenPoints(i0: Int, i1: Int): Int =
        abs(galaxiesExpanded[i1].first - galaxiesExpanded[i0].first) + abs(galaxiesExpanded[i1].second - galaxiesExpanded[i0].second)

    fun part1(): Int {
        // Create combinations
        // List of pairs of the indices
        val combinations: MutableList<Pair<Int, Int>> = mutableListOf()

        for (a in 0..<galaxiesExpanded.size) {
            for (b in a + 1..<galaxiesExpanded.size) {
                combinations.add(Pair(a, b))
            }
        }
        //println(combinations)

        return combinations.sumOf { pair -> distanceBetweenPoints(pair.first, pair.second) }
    }
}