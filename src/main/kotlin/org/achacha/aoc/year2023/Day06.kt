package org.achacha.aoc.year2023

import java.math.BigInteger

/**
 * https://adventofcode.com/2023/day/6
 */
class Day06(input: String) {
    lateinit var durations: List<Long>
    lateinit var records: List<Long>

    init {
        if (input.isNotBlank()) {
            val lines = input.lines()
            if (!lines[0].startsWith("Time:") || !lines[1].startsWith("Distance:")) throw IllegalArgumentException("Input must start with Time: and Distance: prefix")
            durations = lines[0].substring(5).trimIndent().split(" ").filter(String::isNotBlank).map(String::toLong)
            records = lines[1].substring(9).trimIndent().split(" ").filter(String::isNotBlank).map(String::toLong)
            if (durations.size != records.size) throw IllegalArgumentException("Must have the same number of durations and records")
        }
    }

    fun part1(): Long {
        var product = 1L
        var wins = 0
        for (i in durations.indices) {
            val result = findDurationsToBeatRecordDistances(durations[i], records[i])
            if (result > 0) {
                ++wins
                product *= result
            }
        }
        return if (wins > 0) product else 0
    }

    fun part2(): BigInteger {
        var product = BigInteger.ONE
        var wins = 0
        for (i in durations.indices) {
            val result = findDurationsToBeatRecordDistances(durations[i], records[i])
            if (result > 0) {
                ++wins
                product = product.multiply(BigInteger.valueOf(result))
            }
        }
        return if (wins > 0) product else BigInteger.ZERO
    }

    /**
     * How many record wins in given time
     */
    fun findDurationsToBeatRecordDistances(duration: Long, record: Long): Long {
        var wins = 0L
        for (x in 1..<duration) {
            if (x * (duration - x) > record) ++wins
        }
        return wins
    }
}