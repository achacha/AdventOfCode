package org.achacha.aoc.year2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day2401Test {
    @Test
    fun `part 1 sample`() {
        assertEquals(11, Day2401().part1("/year2024/day01_sample.txt"))
    }

    @Test
    fun `part 1`() {
        // 2904518
        println(Day2401().part1("/year2024/day01.txt"))
    }

    @Test
    fun `part 2 sample`() {
        assertEquals(31, Day2401().part2("/year2024/day01_sample.txt"))
    }

    @Test
    fun `part 2`() {
        // 18650129
        println(Day2401().part2("/year2024/day01.txt"))
    }
}