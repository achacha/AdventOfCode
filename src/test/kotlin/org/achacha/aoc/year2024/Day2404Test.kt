package org.achacha.aoc.year2024

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day2404Test {
    @Test
    fun `verify check functions work`() {
        val day = Day2404()
val lines = listOf(
"..........",
"..........",
".S..S..S..",
"..A.A.A...",
"...MMM....",
".SAMXMAS..",
"...MMM....",
"..A.A.A...",
".S..S..S..",
"..........",
)

        assertEquals(1, day.checkE(lines, 4, 5))
        assertEquals(1, day.checkW(lines, 4, 5))
        assertEquals(1, day.checkN(lines, 4, 5))
        assertEquals(1, day.checkS(lines, 4, 5))
        assertEquals(1, day.checkNE(lines, 4, 5))
        assertEquals(1, day.checkNW(lines, 4, 5))
        assertEquals(1, day.checkSE(lines, 4, 5))
        assertEquals(1, day.checkSW(lines, 4, 5))
    }

    @Test
    fun `part 1 sample`() {
        assertEquals(18, Day2404().part1("/year2024/day04_sample.txt"))
    }

    @Test
    fun `part 1`() {
        assertEquals(2575, Day2404().part1("/year2024/day04.txt"))
    }
}