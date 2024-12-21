package org.achacha.aoc.year2024

import org.achacha.common.load2String
import org.apache.commons.lang3.math.NumberUtils

/**
 * https://adventofcode.com/2024/day/2
 */
class Day2403 {
    private val debug = true

    private val regex0 = Regex("mul\\((\\d),(\\d)\\)")
    private val regex = Regex("mul\\(([0-9^,]+),([0-9^,]+)\\)")

    fun part1(resourcePath: String): Long {
        val data = load2String(resourcePath)
        var sumOfProducts: Long = 0
        regex.findAll(data).forEach {
            if (debug) println(it.value)
            val numStr0 = it.groupValues[1]
            val numStr1 = it.groupValues[2]

            // Verify numbers
            if (NumberUtils.isCreatable(numStr0) && NumberUtils.isCreatable(numStr0)) {
                sumOfProducts += numStr0.toLong() * numStr1.toLong()
            }
        }

        return sumOfProducts
    }

    fun part2(resourcePath: String): Int {
        return 0
    }
}