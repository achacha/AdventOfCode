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

    fun extractValidCode(input: String): String {
        var adding = true
        var outputBuffer = StringBuilder()

        // Split with do and if `()` then adding enabled, `n't()` then disable, otherwise just append
        val segments = input.split("do")
        for (i in segments.indices) {
            val segment = segments[i]
            if (segment.startsWith("n't()")) {
                adding = false
                continue
            }
            else if (segment.startsWith("()")) {
                adding = true
                outputBuffer.append(segment.substring(2))
            }
            else if (adding) {
                if (i > 0) outputBuffer.append("do")
                outputBuffer.append(segment)
            }
        }
        return outputBuffer.toString()
    }

    fun part2(resourcePath: String): Long {
        val data = extractValidCode(load2String(resourcePath))
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
}