package org.achacha.aoc.year2025

import org.achacha.common.load2String
import org.achacha.common.stringNumberIncrementer

class Day2502 {
    fun parseInputPart1(resourcePathToData: String): List<Pair<Long, Long>> {
        val rawData = load2String(resourcePathToData)

        val pairs = rawData
            .split(",")
            .map {
                val p = it.split("-")
                Pair(p[0].toLong(), p[1].toLong())
            }

        return pairs
    }

    /**
     * Checks if the whole string is 2 repeating string together
     */
    fun containsRepeating(num: String): String? {
        if (num.isEmpty()) return null
        if (num.length % 2 != 0) return null
        if (num[0] == '0') return null

        val lhs = num.take(num.length / 2)
        return if (num.substring(num.length / 2) == lhs) num else null
    }

    /**
     * Given a string check if there is anything that is repeating
     */
    fun containsRepeatingWithOffset(num: String): String? {
        // start with half size and work to 2
        for (i in num.length / 2 downTo 1) {
            for (leftPos in 0..num.length - 2 * i) {
                if (num[leftPos] == '0') continue    // Skip over leading 0
                //println("`${num.substring(leftPos, leftPos + i)}` =?= `${num.substring(leftPos + i, leftPos + i * 2)}`")
                if (num.substring(leftPos, leftPos + i) == num.substring(leftPos + i, leftPos + i * 2)) return "${num.substring(leftPos, leftPos + i)}${num.substring(leftPos, leftPos + i)}"
            }
        }
        return null
    }

    // TODO?
    fun containsRepeatingAnywhere(num: String): String? {
        // start with half size and work to 2
        for (i in num.length / 2 downTo 1) {
            for (leftPos in 0..num.length - 2 * i) {
                val lhs = num.substring(leftPos, leftPos + i)  // left compare template
                for (rightPos in i..num.length - i) {
                    // compare against right moving window
                    if (lhs == num.substring(rightPos, rightPos + i)) return "$lhs$lhs"
                }
            }
        }
        return null
    }

    fun part1(resourcePathToData: String): Long {
        val idRanges = parseInputPart1(resourcePathToData)
        var result = 0L

        // Iterate over ranges
        idRanges.forEach { (start, end) ->
            //println("--- Range: $start .. $end")
            var current = start.toString()
            while (current.toLong() <= end) {
                //print("Testing: $current")
                containsRepeating(current)?.let {
                    result += it.toLong()
                    //print(" ! found repeating id=$current  pattern=$it  result=$result")
                }
                    //.also { println() }
                current = stringNumberIncrementer(current)
            }
            //println("---")
        }
        return result
    }
}