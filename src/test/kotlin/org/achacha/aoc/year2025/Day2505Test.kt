package org.achacha.aoc.year2025

import org.apache.commons.lang3.tuple.MutablePair
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class Day2505Test {
    @Test
    fun parseInputData() {
        val d = Day2505()
        val inputData = d.parseInputData("/year2025/day2505_sample.txt")

        assertEquals(4, inputData.fresh.size)
        assertEquals(Pair(3,5), inputData.fresh[0])
        assertEquals(6, inputData.produce.size)
        assertTrue(inputData.produce.contains(1))
        assertTrue(inputData.produce.contains(32))
    }

    @Test
    fun part1sample() {
        assertEquals(3, Day2505().part1("/year2025/day2505_sample.txt"))
    }

    @Test
    fun part1() {
        assertEquals(690, Day2505().part1("/year2025/day2505.txt"))
    }

    @Test
    fun compactRanges() {
        val inputData = Day2505.InputData()
        inputData.fresh = mutableListOf(
            MutablePair(1L,4L),
            MutablePair(2L,5L),
            MutablePair(8L,10L),
            MutablePair(10L,12L),
            MutablePair(20L,25L),
            MutablePair(20L,25L),
            MutablePair(22L,23L),
            MutablePair(30L,32L),
            MutablePair(29L,33L)
        )

        assertEquals(4, Day2505().compactRanges(inputData))
    }

    @Test
    fun part2sample() {
        assertEquals(14, Day2505().part2("/year2025/day2505_sample.txt"))
    }

    @Test
    fun part2() {
        assertEquals(344323629240733, Day2505().part2("/year2025/day2505.txt"))
    }

}