package org.achacha.aoc.year2025

import org.achacha.common.CharGrid
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

    fun iterate(grid: CharGrid, ptStart: CharGrid.Point) {
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
}