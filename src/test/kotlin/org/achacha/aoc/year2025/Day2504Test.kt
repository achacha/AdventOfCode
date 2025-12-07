package org.achacha.aoc.year2025

import org.achacha.common.CharGrid
import org.achacha.common.loadCharGrid
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day2504Test {
    @Test
    fun testCountAround() {
        val grid = CharGrid(loadCharGrid("/year2025/day2504_sample.txt"))
        val d = Day2504()
        assertEquals(2, d.countSpaceAroundPoint(grid, 2, 0, '.'))
        assertEquals(0, d.countSpaceAroundPoint(grid, 4, 4, '.'))
        assertEquals(2, d.countSpaceAroundPoint(grid, 0, 7, '@'))
        assertEquals(3, d.countSpaceAroundPoint(grid, 9, 0, '@'))
        assertEquals(2, d.countSpaceAroundPoint(grid, 9, 9, '@'))
    }

    @Test
    fun part1sample() {
        assertEquals(13, Day2504().part1("/year2025/day2504_sample.txt"))
    }

    @Test
    fun part1() {
        assertEquals(1428, Day2504().part1("/year2025/day2504.txt"))
    }
}