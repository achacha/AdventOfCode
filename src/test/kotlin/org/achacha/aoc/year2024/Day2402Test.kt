package org.achacha.aoc.year2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day2402Test {
    @Test
    fun `part1 sample`() {
        assertEquals(2, Day2402().part1("/year2024/day02_sample.txt"))
    }

    @Test
    fun `part 1`() {
        assertEquals(591, Day2402().part1("/year2024/day02.txt"))
    }

    @Test
    fun `test data`() {
        println(Day2402().part2("/year2024/test.txt"))
    }

    @Test
    fun `part2 sample`() {
        assertEquals(4, Day2402().part2("/year2024/day02_sample.txt"))
    }

    @Test
    fun `part 2`() {
        assertEquals(621, Day2402().part2("/year2024/day02.txt"))
    }
}