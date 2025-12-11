package org.achacha.aoc.year2025

import org.achacha.common.CharGrid
import org.achacha.common.GridPoint
import org.achacha.common.load2CharGrid

class Day2507 {
    fun part1(resourcePath: String): Int {
        val grid = load2CharGrid(resourcePath)

        // Find 'S' and iterate
        grid.findFirst('S')?.let { pS ->
            iterate(grid, pS)
        }

        // Find all '^' without '|' above it
        var split = 0
        grid.findAll('^').forEach { pt ->
            if (grid.getAt(pt.x, pt.y - 1, '?') == '|') split++
        }
        return split
    }

    fun iterate(grid: CharGrid, ptStart: GridPoint) {
        if (grid.isNotInGrid(ptStart)) return
//        println("---\n$grid\n---")

        var pt = ptStart.delta(0, 1)
        if (grid.getAt(pt, '?') == '|') return   // already dripped this path
        while (pt.y < grid.maxY()) {
            if (grid.getAt(pt, ' ') == '^') {
                if (grid.getAt(pt.delta(-1, 0), ' ') != '|')
                    grid.setAt(pt.delta(-1, 0), '|')
                iterate(grid, pt.delta(-1, 0))
                grid.setAt(pt.delta(1, 0), '|')
                iterate(grid, pt.delta(1, 0))
                return
            } else {
                grid.setAt(pt, '|')
                pt = pt.delta(0, 1)
            }
        }
    }

    var pathCount: Long = 0L

    private val cache: MutableMap<GridPoint, Long> = mutableMapOf()

    fun part2(resourcePath: String): Long {
        val grid = load2CharGrid(resourcePath)

        // Find 'S' and iterate
        grid.findFirst('S')?.let { pS ->
            val result = iterate2(grid, pS)
//            println("Cache: $cache")
            return result
        }

        return 0
    }

    /**
     * Will advance ptStart y+1 and calculate this node
     * @param ptStart point of start
     */
    fun iterate2(grid: CharGrid, ptStart: GridPoint): Long {
//        println("---\n$grid\n---")
        var pt = ptStart.delta(0, 1)

        // If hit bottom we have a path
//        if (pt.y >= grid.maxY()) {
//            println("End reached: $pt")
//            return 1
//        }
        if (grid.isNotInGrid(pt)) {
            // Out of range may be outsde of the grid left/right
//            println("Bottom reached, no space:  $pt")
            return 1
        }

        var paths = 0L
        while (pt.y < grid.maxY()) {
            // Check if this one is cached
//        println("Checking cache for $pt")
            cache[pt]?.let {
//                println("Cached: $pt: $it")
                return it
            }

            if (grid.getAt(pt, ' ') == '^') {
                // Go left
                val ptLeft = pt.delta(-1, 0)
                if (ptLeft.x >= 0) {
//                    grid.setAt(ptLeft, '|')
                    paths += iterate2(grid, ptLeft)
                }

                // Go right
                val ptRight = pt.delta(1, 0)
                if (ptRight.x < grid.maxX(ptRight.y)) {
//                    grid.setAt(ptRight, '|')
                    paths += iterate2(grid, ptRight)
                }

                if (paths > 0) {
                    cache[pt] = paths
//                    println("Caching SET: $pt: $paths")
                }

                return paths
            } else {
//                grid.setAt(pt, '|')
                pt = pt.delta(0, 1)

                // Reached bottom without a split
                if (pt.y >= grid.maxY()) {
//                    println("--$pt")
                    return 1
                }
            }
        }

        return 0
    }
}