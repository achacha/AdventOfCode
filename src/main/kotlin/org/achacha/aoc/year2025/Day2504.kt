package org.achacha.aoc.year2025

import org.achacha.common.CharGrid
import org.achacha.common.loadCharGrid

class Day2504 {
    fun readData(resourcePath: String): CharGrid {
        return CharGrid(loadCharGrid(resourcePath))
    }

    /**
     * Count empty spaces around
     * Empty space `.`
     */
    fun countSpaceAroundPoint(grid: CharGrid, x: Int, y: Int, c: Char): Int {
        var sum = 0
        val default = Char(0)
        // top
        for (i in -1..1) sum += (if (grid.getAt(x - i, y - 1, default) == c) 1 else 0)
        //bottom
        for (i in -1..1) sum += (if (grid.getAt(x - i, y + 1, default) == c) 1 else 0)
        // left
        sum += if (grid.getAt(x - 1, y, default) == c) 1 else 0
        // right
        sum += if (grid.getAt(x + 1, y, default) == c) 1 else 0

        return sum
    }

    fun part1(resourcePath: String): Int {
        var count = 0
        val grid = readData(resourcePath)
        for (y in 0..<grid.maxY()) {
            for (x in 0..<grid.maxX(y)) {
                if (grid.getAt(x, y, '!') == '@') {
                    val cnt = countSpaceAroundPoint(grid, x, y, '@')
                    println("($x,$y) = $cnt")
                    if (cnt < 4) count++
                }
            }
        }
        return count
    }

    fun findAllRemovable(grid: CharGrid): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        for (y in 0..<grid.maxY()) {
            for (x in 0..<grid.maxX(y)) {
                if (grid.getAt(x, y, '!') == '@') {
                    val cnt = countSpaceAroundPoint(grid, x, y, '@')
                    //println("Count: ($x,$y) = $cnt")
                    if (cnt < 4) {
                        result.add(Pair(x, y))
                        //println("Remove: ($x,$y) = $cnt")
                    }
                }
            }
        }
        return result
    }

    /**
     * Remove in place
     * @return CharGrid passed in for convenience
     */
    fun removePoints(grid: CharGrid, toRemove: List<Pair<Int, Int>>): CharGrid {
        for (point in toRemove) {
            grid.setAt(point.first, point.second, '.')
        }
        return grid
    }

    fun part2(resourcePath: String): Int {
        val grid = readData(resourcePath)

        var removedTotal = 0
        do {
            val removable = findAllRemovable(grid)
            removePoints(grid, removable)
            removedTotal += removable.size
        } while (removable.isNotEmpty())

        return removedTotal
    }
}