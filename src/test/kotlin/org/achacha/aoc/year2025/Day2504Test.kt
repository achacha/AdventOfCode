package org.achacha.aoc.year2025

import org.achacha.common.CharGrid
import org.achacha.common.load2ArrayOfCharArray
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day2504Test {
    @Test
    fun testCountAround() {
        val grid = CharGrid(load2ArrayOfCharArray("/year2025/day2504_sample.txt"))
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

    @Test
    fun findAllRemovable() {
        val grid = CharGrid(load2ArrayOfCharArray("/year2025/day2504_sample.txt"))
        val d = Day2504()

        // step 0
        val found0 = d.findAllRemovable(grid)
        assertEquals(13, found0.size)
        d.removePoints(grid, found0)

        // step 1
        val found1 = d.findAllRemovable(grid)
        assertEquals(12, found1.size)
        d.removePoints(grid, found1)

        // step 2
        val found2 = d.findAllRemovable(grid)
        assertEquals(7, found2.size)
        d.removePoints(grid, found2)

        // step 3
        val found3 = d.findAllRemovable(grid)
        assertEquals(5, found3.size)
        d.removePoints(grid, found3)

        // step 4
        val found4 = d.findAllRemovable(grid)
        assertEquals(2, found4.size)
        d.removePoints(grid, found4)

        // step 5 (1)
        val found5 = d.findAllRemovable(grid)
        assertEquals(1, found5.size)
        d.removePoints(grid, found5)

        // step 6 (1)
        val found6 = d.findAllRemovable(grid)
        assertEquals(1, found6.size)
        d.removePoints(grid, found6)

        // step 7 (1)
        val found7 = d.findAllRemovable(grid)
        assertEquals(1, found7.size)
        d.removePoints(grid, found7)

        // step 8 (1)
        val found8 = d.findAllRemovable(grid)
        assertEquals(1, found8.size)
        d.removePoints(grid, found8)

        // step 9
        val found9 = d.findAllRemovable(grid)
        assertEquals(0, found9.size)
    }

    @Test
    fun part2sample() {
        assertEquals(43, Day2504().part2("/year2025/day2504_sample.txt"))
    }

    @Test
    fun part2() {
        assertEquals(8936, Day2504().part2("/year2025/day2504.txt"))
    }

}