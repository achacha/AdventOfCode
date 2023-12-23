package org.achacha.aoc.year2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigInteger

class Day06Test {
    @Test
    fun `test duration test`() {
        assertEquals(4, Day06("").findDurationsToBeatRecordDistances(7, 9))
        assertEquals(8, Day06("").findDurationsToBeatRecordDistances(15, 40))
        assertEquals(9, Day06("").findDurationsToBeatRecordDistances(30, 200))
    }

    @Test
    fun `test part 1`() {
        assertEquals(288, Day06(sampleDataPart1).part1())
        assertEquals(2612736, Day06(fullDataPart1).part1())
        Long.MAX_VALUE
    }

    @Test
    fun `test part 2`() {
        assertEquals(BigInteger.valueOf(71503L), Day06(sampleDataPart2).part2())
        assertEquals(BigInteger.valueOf(29891250L), Day06(fullDataPart2).part2())
    }

    val sampleDataPart1 = """
Time:      7  15   30
Distance:  9  40  200
""".trimIndent()

    val fullDataPart1 = """
Time:        45     97     72     95
Distance:   305   1062   1110   1695
""".trimIndent()

    val sampleDataPart2 = """
Time:      71530
Distance:  940200
""".trimIndent()

    val fullDataPart2 = """
Time:        45977295
Distance:   305106211101695
""".trimIndent()
}